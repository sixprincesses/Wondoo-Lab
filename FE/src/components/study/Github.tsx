import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import useGetBranches from "../../apis/tempFeed/useGetBranches";
import useGetCommits from "../../apis/tempFeed/useGetCommits";
import useGetFiles from "../../apis/tempFeed/useGetFiles";
import useGetRepos from "../../apis/tempFeed/useGetRepos";
import { dummySelect } from "../../assets/data/Github";
import angleBracket from "../../assets/icon/angleBracket.png";
import closeIcon from "../../assets/icon/closeIcon.png";
import drag from "../../assets/icon/drag.png";
import { lineInfo } from "../../interfaces/Line";
import { File } from "../../interfaces/tempFeed/File";
import { GetBranchesParams } from "../../interfaces/tempFeed/GetBranchesParams";
import { GetCommitsParams } from "../../interfaces/tempFeed/GetCommitsParams";
import { GetFilesParams } from "../../interfaces/tempFeed/GetFilesParams";
import { useAppDispatch } from "../../store/hooks";
import { setInstanceData } from "../../store/tempFeedSlice";
import { OptionProps } from "../../stories/select/SelectInterface";
import Select from "../common/Select";
import GithubFile from "./GithubFile";
import {
  CloseBtn,
  DragBtn,
  FileBox,
  Form,
  Header,
  Main,
  NicknameBox,
  Rollup,
  SelectBox,
  Title,
  Wrapper,
} from "./GithubStyle";

interface GithubProps {
  id: number;
  deleteData: (id: number) => void;
}

const Github = ({ id, deleteData }: GithubProps) => {
  const dispatch = useAppDispatch();
  const getRepos = useGetRepos();
  const getBranches = useGetBranches();
  const getCommits = useGetCommits();
  const getFiles = useGetFiles();

  const [githubNickname, setGithubNickname] = useState("");
  const [repo, setRepo] = useState<dummySelect>({
    selected: { value: "default", children: "레포지토리" },
    options: [{ value: "direct", children: "직접 입력" }],
  });
  const [branch, setBranch] = useState<dummySelect>({
    selected: { value: "default", children: "브랜치" },
    options: [],
  });
  const [commit, setCommit] = useState<dummySelect>({
    selected: { value: "default", children: "커밋 메세지" },
    options: [],
  });
  const [files, setFiles] = useState<File[]>([]);

  // 깃허브 닉네임 선택 로직
  const [isNickname, setIsNickname] = useState(false);
  const handleGithubNickname = (e: ChangeEvent<HTMLInputElement>) => {
    setGithubNickname(e.target.value);
  };
  const submitGithubNickname = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsNickname(!isNickname);
    if (isNickname) return;
    const newRepos = await getRepos({ nickname: githubNickname });
    if (!newRepos) return;
    setRepo((prev) => ({
      ...prev,
      options: [
        { value: "direct", children: "직접 입력" },
        ...newRepos.map((repo) => ({ value: repo, children: repo })),
      ],
    }));
  };

  // 레포지토리 선택 로직
  useEffect(() => {
    if (repo.selected.value === "default" || repo.selected.value === "direct")
      return;
    const setNewBranches = async () => {
      const params: GetBranchesParams = {
        nickname: githubNickname,
        repository: repo.selected.value,
      };
      const newBranches = await getBranches(params);
      if (!newBranches) return;
      setBranch((prev) => ({
        ...prev,
        options: [
          ...newBranches.map((branch) => ({ value: branch, children: branch })),
        ],
      }));
    };
    setNewBranches();
  }, [repo.selected.value]);

  // 브랜치 선택 로직
  useEffect(() => {
    if (branch.selected.value === "default") return;
    const setNewCommits = async () => {
      const params: GetCommitsParams = {
        nickname: githubNickname,
        repository: repo.selected.value,
        branch: branch.selected.value,
      };
      const newCommits = await getCommits(params);
      if (!newCommits) return;
      setCommit((prev) => ({
        ...prev,
        options: [
          ...newCommits.map((commit) => ({
            value: commit.sha,
            children: commit.message,
          })),
        ],
      }));
    };
    setNewCommits();
  }, [branch.selected.value]);

  // 커밋 선택 로직
  useEffect(() => {
    if (commit.selected.value === "default") return;
    const setNewFiles = async () => {
      const params: GetFilesParams = {
        nickname: githubNickname,
        repository: repo.selected.value,
        commit: commit.selected.value,
      };
      const newFiles = await getFiles(params);
      if (!newFiles) return;
      setFiles(newFiles);
    };
    setNewFiles();
  }, [commit.selected.value]);

  // 롤업 로직
  const [isRolled, setIsRolled] = useState(false);
  const handleRollup = () => {
    setIsRolled(!isRolled);
  };

  // 커밋 직접 입력 로직
  const [commitUrlInput, setCommitUrlInput] = useState<string>("");
  const [commitUrl, setCommitUrl] = useState<string>("");
  const handleCommitUrl = (e: ChangeEvent<HTMLInputElement>): void => {
    setCommitUrlInput(e.target.value);
  };
  const submitCommitUrl = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    setCommitUrl(commitUrlInput);
    setCommitUrlInput("");
  };

  // 데이터 저장 로직
  const [fileData, setFileData] = useState(new Map<string, lineInfo[]>());
  useEffect(() => {
    let headerContent: string = "";
    let nickname: string = "";
    if (githubNickname) {
      nickname =
        '<span class="githubNickname">' + githubNickname + " / </span>\n";
    } else {
      nickname = "";
    }
    if (repo.selected.value !== "direct") {
      // 헤더를 레포/브랜치/커밋 으로 선택한 경우
      const setName = (option: OptionProps, name: string): string => {
        const { value, children } = option;
        if (value === "default") return "";
        if (name === "commit") {
          return '<sapn class="' + name + '">' + children + "</sapn>\n";
        }
        return '<sapn class="' + name + '">' + children + " / </sapn>\n";
      };
      const repoName: string = setName(repo.selected, "repo");
      const branchName: string = setName(branch.selected, "branch");
      const commitName: string = setName(commit.selected, "commit");
      headerContent = nickname + repoName + branchName + commitName;
    } else {
      // 헤더를 직접 입력으로 선택한 경우
      headerContent = '<div class="commitUrl">' + commitUrl + "</div>\n";
    }
    const header: string =
      '<div class="githubHeader">\n' +
      "커밋경로 : " +
      headerContent +
      "</div>\n";

    // 바디
    const setPart = (part: string | number): string => {
      // 각 줄의 부분
      // console.log(part);

      if (typeof part === "number") {
        return "<p>" + part.toString() + "</p>\n";
      }
      return "<p>" + part + "</p>\n";
    };
    const setLine = (line: (string | number)[]) => {
      // 한 줄
      let className = "";
      switch (line[2]) {
        case " ":
          className = "line-info";
          break;
        case "+":
          className = "add";
          break;
        case "-":
          className = "sub";
          break;
        case "":
          className = "none";
      }
      const parts = line.map((part: string | number) => setPart(part)).join("");

      return '<div class="line ' + className + '">\n' + parts + "</div>";
    };
    const setFile = (filename: string, lines: lineInfo[]) => {
      // 파일 이름
      const name = '<div class="fileName">' + filename + "</div>\n";
      // 파일 내용
      lines.sort((a, b) => a.lineNo - b.lineNo);
      const fileConent =
        '<div class="fileContent">\n' +
        lines.map((line: lineInfo) => setLine(line.value)).join("\n") +
        "\n</div>";

      return '<div class="file">\n' + name + fileConent + "\n</div>\n";
    };
    const files = Array.from(fileData)
      .map(([filename, lines]) => setFile(filename, lines))
      .join("");

    const body = '<div class="githubBody">\n' + files + "</div>";

    const github = '<div class="github">\n' + header + body + "\n</div>";
    dispatch(setInstanceData({ id, data: ["github", github] }));
  }, [fileData, repo.selected, branch.selected, commit.selected, commitUrl]);

  // 초기화 로직
  useEffect(() => {
    setBranch({
      ...branch,
      selected: { value: "default", children: "브랜치" },
    });
    setCommitUrl("");
  }, [repo.selected.value]);
  useEffect(() => {
    setCommit({
      ...commit,
      selected: { value: "default", children: "커밋 메세지" },
    });
  }, [branch.selected.value]);
  useEffect(() => {
    setFiles([]);
    setFileData(new Map<string, any[]>());
    setTimeout(() => {
      if (commit.selected.value !== "default") {
        setFiles([]);
      } else if (commitUrl !== "") {
        setFiles([]);
      }
    }, 100);
  }, [commit.selected.value, commitUrl]);

  return (
    <Wrapper>
      <Header>
        <Rollup onClick={handleRollup} isRolled={isRolled}>
          <img src={angleBracket} alt="펼치기" />
        </Rollup>
        <Title>Github</Title>
        <DragBtn>
          <img src={drag} alt="드래그" />
        </DragBtn>
        <CloseBtn onClick={() => deleteData(id)}>
          <img src={closeIcon} alt="닫기" />
        </CloseBtn>
      </Header>
      <Main isRolled={isRolled}>
        <NicknameBox>
          <Form onSubmit={submitGithubNickname}>
            <div>
              <input
                value={githubNickname}
                onChange={handleGithubNickname}
                placeholder="깃허브 닉네임을 입력하세요."
                disabled={isNickname}
              />
            </div>
            <button>{isNickname ? "재입력" : "입력"}</button>
          </Form>
        </NicknameBox>
        {isNickname ? (
          <SelectBox>
            <Select state={repo} setSelected={setRepo} />
            {repo.selected.value !== "default" &&
            repo.selected.value !== "direct" ? (
              <>
                <Select state={branch} setSelected={setBranch} />
                {branch.selected.value !== "default" && (
                  <Select state={commit} setSelected={setCommit} />
                )}
              </>
            ) : null}
            {repo.selected.value === "direct" ? (
              <Form onSubmit={submitCommitUrl}>
                <div>
                  <input
                    value={commitUrlInput}
                    onChange={handleCommitUrl}
                    placeholder="커밋 URL 을 입력하세요"
                  />
                </div>
                <button>입력</button>
              </Form>
            ) : null}
          </SelectBox>
        ) : null}
        <FileBox>
          {files.length
            ? files.map((file, idx) => (
                <GithubFile
                  key={idx}
                  file={file}
                  id={id}
                  data={fileData}
                  setData={setFileData}
                />
              ))
            : null}
        </FileBox>
      </Main>
    </Wrapper>
  );
};

export default Github;

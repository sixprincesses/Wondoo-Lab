import axios, { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useEffect, useState } from "react";
import { Button } from "../button/Button";
import { CardFile } from "../cardFile/CardFile";
import { CardFileProps } from "../cardFile/CardFileInterface";
import { Input } from "../input/Input";
import { Select } from "../select/Select";
import { OptionProps, SelectProps } from "../select/SelectInterface";
import "./InstanceGithub.css";
import { InstanceGithubProps } from "./InstanceGithubInterface";
import { branch } from "./InterfaceBranch";
import { commit } from "./InterfaceCommit";
import { repo } from "./InterfaceRepo";

const InstanceGithub = ({ userName = "KTaeGyu" }: InstanceGithubProps) => {
  // 변수
  const [isLoading, setIsLoading] = useState(false);
  const [commitUrlInput, setCommitUrlInput] = useState("");
  const [repoInfo, setRepoInfo] = useState<SelectProps>({
    title: { value: "title", children: "레포지토리 선택" },
    defaultOption: { value: "Direct", children: "커밋 URL 입력" },
    options: [],
  });
  const [brachInfo, setBranchInfo] = useState<SelectProps>({
    title: { value: "title", children: "브랜치 선택" },
    options: [],
  });
  const [commitInfo, setCommitInfo] = useState<SelectProps>({
    title: { value: "title", children: "커밋 메세지 선택" },
    options: [],
  });
  const [files, setFiles] = useState([]);

  // 이벤트 핸들러
  const handleCommitUrlInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    setCommitUrlInput(value);
  };
  const handleCommitUrlChange = (e: React.SyntheticEvent) => {
    e.preventDefault();
    const splittedUrl = commitUrlInput.split("/");
    const userName = splittedUrl[3];
    const repos = splittedUrl[4];
    const sha = splittedUrl[6];

    const fetchFiles = async (params: RawAxiosRequestConfig) => {
      setIsLoading(true);
      await axios
        .request(params)
        .then((res: AxiosResponse) => {
          setFiles(res.data.files);
        })
        .catch((err: AxiosError) => {
          console.error(err);
        })
        .finally(() => {
          setIsLoading(false);
        });
    };
    const params = {
      method: "GET",
      url: `https://api.github.com/repos/${userName}/${repos}/commits/${sha}`,
      headers: {
        Accept: "*/*",
      },
    };
    fetchFiles(params);
  };

  // 통신
  useEffect(() => {
    const fetchRepos = async (params: RawAxiosRequestConfig) => {
      setIsLoading(true);
      await axios
        .request(params)
        .then((res: AxiosResponse) => {
          const options: OptionProps[] = res.data.map((repo: repo) => {
            return { value: repo.name, children: repo.name };
          });
          setRepoInfo({
            title: { value: "title", children: "레포지토리 선택" },
            defaultOption: { value: "Direct", children: "커밋 URL 입력" },
            options: options,
          });
          setBranchInfo({
            title: { value: "title", children: "브랜치 선택" },
            options: [],
          });
          setCommitInfo({
            title: { value: "title", children: "커밋 메세지 선택" },
            options: [],
          });
          setFiles([]);
        })
        .catch((err: AxiosError) => {
          console.error(err);
          setRepoInfo({
            title: { value: "title", children: "레포지토리 선택" },
            options: [],
          });
          alert("유효한 사용자 아이디가 아닙니다.");
        })
        .finally(() => {
          setIsLoading(false);
        });
    };
    const params: RawAxiosRequestConfig = {
      method: "GET",
      url: `https://api.github.com/users/${userName}/repos`,
      headers: {
        Accept: "*/*",
      },
    };
    if (userName.trim() !== "" && repoInfo.title?.value === "title") {
      setRepoInfo({
        title: { value: "title", children: "레포지토리 선택" },
        defaultOption: { value: "Direct", children: "커밋 URL 입력" },
        options: [],
      });
      setBranchInfo({
        title: { value: "title", children: "브랜치 선택" },
        options: [],
      });
      setCommitInfo({
        title: { value: "title", children: "커밋 메세지 선택" },
        options: [],
      });
      fetchRepos(params);
    }
  }, [userName]);
  useEffect(() => {
    const fetchBraches = async (params: RawAxiosRequestConfig) => {
      setIsLoading(true);
      await axios
        .request(params)
        .then((res: AxiosResponse) => {
          const options: OptionProps[] = res.data.map((branch: branch) => {
            return { value: branch.name, children: branch.name };
          });
          setBranchInfo({
            title: { value: "title", children: "브랜치 선택" },
            options: options,
          });
          setCommitInfo({
            title: { value: "title", children: "커밋 메세지 선택" },
            options: [],
          });
          setFiles([]);
        })
        .catch((err: AxiosError) => {
          console.error(err);
          setBranchInfo({
            title: { value: "title", children: "브랜치 선택" },
            options: [],
          });
          alert("가능한 레포지토리가 아닙니다.");
        })
        .finally(() => {
          setIsLoading(false);
        });
    };
    const params: RawAxiosRequestConfig = {
      method: "GET",
      url: `https://api.github.com/repos/${userName}/${repoInfo.title?.value}/branches`,
      headers: {
        Accept: "*/*",
      },
    };
    if (
      repoInfo.title?.value !== "title" &&
      repoInfo.title?.value !== "Direct"
    ) {
      setBranchInfo({
        title: { value: "title", children: "브랜치 선택" },
        options: [],
      });
      setCommitInfo({
        title: { value: "title", children: "커밋 메세지 선택" },
        options: [],
      });
      fetchBraches(params);
    }
  }, [repoInfo.title?.value]);
  useEffect(() => {
    const fetchCommits = async (params: RawAxiosRequestConfig) => {
      setIsLoading(true);
      await axios
        .request(params)
        .then((res: AxiosResponse) => {
          const options: OptionProps[] = res.data.map((commit: commit) => {
            return { value: commit.sha, children: commit.commit?.message };
          });
          setCommitInfo({
            title: { value: "title", children: "커밋 메세지 선택" },
            options: options,
          });
          setFiles([]);
        })
        .catch((err: AxiosError) => {
          console.error(err);
          setCommitInfo({
            title: { value: "title", children: "커밋 메세지 선택" },
            options: [],
          });
          alert("가능한 브랜치가 아닙니다.");
        })
        .finally(() => {
          setIsLoading(false);
        });
    };
    const params: RawAxiosRequestConfig = {
      method: "GET",
      url: `https://api.github.com/repos/${userName}/${repoInfo.title?.value}/commits?sha=${brachInfo.title?.value}`,
      headers: {
        Accept: "*/*",
      },
    };
    if (brachInfo.title?.value !== "title") {
      fetchCommits(params);
    }
  }, [brachInfo.title?.value]);
  useEffect(() => {
    const fetchFiles = async (params: RawAxiosRequestConfig) => {
      setIsLoading(true);
      await axios
        .request(params)
        .then((res: AxiosResponse) => {
          setFiles(res.data.files);
        })
        .catch((err: AxiosError) => {
          console.error(err);
          setFiles([]);
          alert("가능한 커밋 내역이 아닙니다.");
        })
        .finally(() => {
          setIsLoading(false);
        });
    };
    const params: RawAxiosRequestConfig = {
      method: "GET",
      url: `https://api.github.com/repos/${userName}/${repoInfo.title?.value}/commits/${commitInfo.title?.value}`,
      headers: {
        Accept: "*/*",
      },
    };
    if (commitInfo.title?.value !== "title") {
      fetchFiles(params);
    }
  }, [commitInfo.title?.value]);

  return (
    <div className="github-box">
      {isLoading ? (
        <h1>Loading</h1>
      ) : (
        <div className="container">
          <div className="instance-name">
            <h2>Github Instance</h2>
          </div>
          <div className="select-box">
            <Select
              defaultOption={repoInfo.defaultOption}
              options={repoInfo.options}
              prop={repoInfo}
              setProp={setRepoInfo}
            ></Select>
            {repoInfo.title?.value === "Direct" && (
              <form
                className="commit-input-box"
                onSubmit={handleCommitUrlChange}
              >
                <Input
                  onChange={handleCommitUrlInput}
                  value={commitUrlInput}
                  label="Commit URL"
                  width="auto"
                />
                <Button type="submit" size="small" label="입력"></Button>
              </form>
            )}
            {repoInfo.title?.value !== "Direct" &&
              repoInfo.title?.value !== "title" && (
                <Select
                  options={brachInfo.options}
                  prop={brachInfo}
                  setProp={setBranchInfo}
                ></Select>
              )}
            {repoInfo.title?.value !== "Direct" &&
              repoInfo.title?.value !== "title" &&
              brachInfo.title?.value !== "title" && (
                <Select
                  className="flex"
                  width="100%"
                  options={commitInfo.options}
                  prop={commitInfo}
                  setProp={setCommitInfo}
                ></Select>
              )}
          </div>
          <div className="commit-content-box">
            {files?.map((file: CardFileProps) => {
              return (
                <CardFile
                  filename={file.filename}
                  changes={file.changes}
                  patch={file.patch}
                />
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
};

export { InstanceGithub };

import { useEffect, useState } from "react";
import {
  ButtonCreateDiary,
  DivButtonAndInfoContainer,
  DivCommitContainer,
  DivCommitContent,
  DivCommitHeader,
  DivDiaryContainer,
  DivDiaryContent,
  DivDiaryContentContainer,
  DivDiaryHeader,
  DivDiaryHeaderRight,
  DivGitLogContent,
  DivRepoName,
  DivUserDiaryContainer,
} from "./UserDiaryStyle";
import infoIcon from "../../assets/icon/info.png";
import closeIcon from "../../assets/icon/closeIcon.png";
import repoIcon from "../../assets/img/branchIcon.png";
import commitIcon from "../../assets/img/commitIcon.png";
import clipboardIcon from "../../assets/img/clipboardIcon.png";
import useGetDiary from "../../apis/member/useGetDiary";
import { useParams } from "react-router-dom";
import usePostDiary from "../../apis/member/usePostDiary";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import useDeleteDiary from "../../apis/member/useDeleteDiary";

interface Repository {
  commit_id: string;
  time: string;
  content: string;
}

interface Diary {
  diary_id: string;
  content: string;
  date: string;
  repository: string[];
  commit_count: number;
  log: {
    [key: string]: Repository[];
  };
}

const UserDiary = () => {
  const [diaryList, setDiaryList] = useState<Diary[]>([]);
  const [isDetail, setIsDetail] = useState<boolean[]>([]);
  const [isLogDetail, setIsLogDetail] = useState<boolean[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const getDiary = useGetDiary();
  const postDiary = usePostDiary();
  const deleteDiary = useDeleteDiary();
  const { memberId } = useParams();
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  useEffect(() => {
    if (memberId === undefined) return;
    const fetchData = async () => {
      setIsLoading(true);
      const newDiaryDataList: Diary[] = await getDiary(+memberId, {
        page: 0,
        size: 5,
        sort: "date,desc",
      }).finally(() => setIsLoading(false));

      // console.log(newDiaryDataList);
      setDiaryList(newDiaryDataList);
    };

    fetchData();
  }, []);

  useEffect(() => {
    setIsDetail(new Array(diaryList.length).fill(false));
    setIsLogDetail(new Array(diaryList.length).fill(false));
  }, [diaryList]);

  const handleClickContent = (idx: number) => {
    const newIsDetail = [...isDetail];
    newIsDetail[idx] = !newIsDetail[idx];
    setIsDetail(newIsDetail);
  };

  const handleClickLogContent = (idx: number) => {
    const newIsLogDetail = [...isLogDetail];
    newIsLogDetail[idx] = !newIsLogDetail[idx];
    setIsLogDetail(newIsLogDetail);
  };

  const handleCopyClipboard = async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      alert("클립보드에 복사되었습니다.");
    } catch (e) {
      alert("복사 실패하였습니다.");
    }
  };

  const handleCreateDiary = async () => {
    setIsLoading(true);
    await postDiary().finally(() => setIsLoading(false));
  };

  const handleDeleteDiary = async (idx: number) => {
    setIsLoading(true);
    await deleteDiary({ diary_id: diaryList[idx].diary_id }).finally(() =>
      setIsLoading(false)
    );
  };

  if (isLoading) {
    return <></>;
  }

  return (
    <DivUserDiaryContainer>
      {memberId && +memberId === userId ? (
        <DivButtonAndInfoContainer>
          <img src={infoIcon} alt="정보" />
          <p>직접 생성하지 않아도 오전 6시마다 새로 생성됩니다.</p>
          <ButtonCreateDiary onClick={handleCreateDiary}>
            다이어리 생성
          </ButtonCreateDiary>
        </DivButtonAndInfoContainer>
      ) : null}
      {diaryList &&
        diaryList.map((diary, idx) => (
          <DivDiaryContainer key={idx}>
            <DivDiaryHeader>
              <h3 onClick={() => handleClickContent(idx)}>{diary.date}</h3>
              {isDetail[idx] ? (
                <DivDiaryHeaderRight>
                  <p>레포지토리 {diary.repository.length}</p>
                  <p>커밋 {diary.commit_count}</p>
                  <p className="log" onClick={() => handleClickLogContent(idx)}>
                    {isLogDetail[idx] ? "로그 닫기" : "로그 보기"}
                  </p>
                </DivDiaryHeaderRight>
              ) : (
                <DivDiaryHeaderRight>
                  <img
                    src={closeIcon}
                    alt="삭제"
                    onClick={() => handleDeleteDiary(idx)}
                  />
                </DivDiaryHeaderRight>
              )}
            </DivDiaryHeader>
            <DivDiaryContentContainer>
              {isDetail[idx] ? (
                <DivDiaryContent
                  className={isLogDetail[idx] ? "is-log-on" : undefined}
                >
                  {diary.content}
                </DivDiaryContent>
              ) : null}
              {isDetail[idx] && isLogDetail[idx]
                ? diary.repository.map((repository) => (
                    <DivGitLogContent key={repository}>
                      <DivRepoName>
                        <img src={repoIcon} />
                        <span>{repository}</span>
                      </DivRepoName>
                      {diary.log[repository].map((commit) => (
                        <DivCommitContainer
                          key={new Date(commit.time).getMilliseconds()}
                        >
                          <DivCommitHeader>
                            <img src={commitIcon} />
                            <span>
                              commits on {new Date(commit.time).getHours()} :{" "}
                              {new Date(commit.time).getMinutes() < 10
                                ? "0" + new Date(commit.time).getMinutes()
                                : new Date(commit.time).getMinutes()}
                            </span>
                            <DivCommitContent>
                              <img
                                src={clipboardIcon}
                                onClick={() =>
                                  handleCopyClipboard(commit.content)
                                }
                              />
                              {commit.content}
                            </DivCommitContent>
                          </DivCommitHeader>
                        </DivCommitContainer>
                      ))}
                    </DivGitLogContent>
                  ))
                : null}
            </DivDiaryContentContainer>
          </DivDiaryContainer>
        ))}
    </DivUserDiaryContainer>
  );
};

export default UserDiary;

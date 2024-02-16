import { ChangeEvent, KeyboardEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import usePostFeed from "../../apis/feed/usePostFeed.tsx";
import Feed from "../../components/main/Feed.tsx";
import { feed, member } from "../../interfaces/feed/FeedState.tsx";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import { StudyCancle, StudyConfirm, StudyFooterBtns } from "./StudyStyle.tsx";
import * as Styled from "./UploadStyle.tsx";

const Upload = () => {
  const navigate = useNavigate();
  const postFeed = usePostFeed();

  // 타이틀 로직
  const [title, setTitle] = useState<string>("");
  const handleTitle = (e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  // 키워드 로직
  const [keywords, setKeywords] = useState<string[]>([]);
  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    const { key, currentTarget } = e;
    const newKeyword = currentTarget.value.trim();

    if (
      key === "Enter" ||
      key === " " ||
      key === "Backspace" ||
      key === "Tab"
    ) {
      if (key === "Enter" || key === " " || key === "Tab") {
        if (keywords.length > 5) {
          alert("키워드는 5개를 초과할 수 없습니다.");
          e.preventDefault();
          return;
        }
        if (newKeyword !== "") {
          // Check if the newKeyword already exists in keywords
          if (!keywords.includes(newKeyword)) {
            setKeywords((prevKeywords) => [...prevKeywords, newKeyword]);
          }
          currentTarget.value = "";
          e.preventDefault();
        }
      } else if (
        key === "Backspace" &&
        newKeyword === "" &&
        keywords.length > 0
      ) {
        setKeywords((prevKeywords) => prevKeywords.slice(0, -1));
        e.preventDefault();
      }
    }
  };
  const handleKeywordDelete = (index: number) => {
    setKeywords((prevKeywords) =>
      prevKeywords.filter((_, idx) => idx !== index)
    );
  };
  // 키워드 창 포커스 로직
  const [isFocus, setIsFocus] = useState(false);
  const handleKeywordFocus = () => {
    setIsFocus(true);
  };
  const handleKeywordBlur = () => {
    setIsFocus(false);
  };

  // 나머지 변수들
  const feedContent = useAppSelector(
    (state: RootState) => state.tempFeed.data.instanceData
  );
  const time_logs = useAppSelector(
    (state: RootState) => state.tempFeed.data.timelogs
  );
  const total_time = useAppSelector(
    (state: RootState) => state.tempFeed.data.totalTime
  );
  const user = useAppSelector((state: RootState) => state.user.userInfo);
  const member: member = {
    member_id: user.member_id,
    nickname: user.nickname,
    image_id: user.image_id,
    level: user.level,
  };

  // 피드 로직
  const [feed, setFeed] = useState<feed>({
    feed_id: -2,
    member: member,
    title: title,
    content: feedContent,
    keywords: keywords,
    time_logs: time_logs,
    total_time: total_time,
    created_time: new Date(),
    updated_time: new Date(),
    is_liked: false,
    is_bookmarked: false,
  });
  useEffect(() => {
    setFeed({
      feed_id: -2,
      member: member,
      title: title,
      content: feedContent,
      keywords: keywords,
      time_logs: [],
      total_time: 10,
      created_time: new Date("2024-02-14T18:00:00"),
      updated_time: new Date("2024-02-14T18:00:00"),
      is_liked: false,
      is_bookmarked: false,
    });
  }, [title, keywords, feedContent]);

  // 피드 포스트 로직
  const FeedPost = () => {
    if (title === "") {
      alert("빈 제목은 등록할 수 없습니다.");
      return;
    }
    if (feedContent === "[]") {
      alert("빈 게시물은 등록할 수 없습니다.");
      return;
    }
    if (time_logs?.length === 0) {
      // console.log("타임로그 없음 에러!");
    }

    const postBody = {
      feed_id: 1,
      title: title,
      content: feedContent,
      keywords: keywords,
      time_logs: time_logs,
    };
    postFeed(postBody);
  };

  return (
    <Styled.Wrapper>
      <Styled.Preview>
        <div>
          <Feed feed={feed} />
        </div>
      </Styled.Preview>
      <div />
      <Styled.Inputs>
        <Styled.TitleBox>
          <h2>제목</h2>
          <div />
          <input type="text" value={title} onChange={handleTitle} />
        </Styled.TitleBox>
        <Styled.KeywordBox>
          <h2>키워드</h2>
          <div />
          <Styled.Keywords isFocus={isFocus}>
            {keywords.map((keyword, idx) => (
              <Styled.Keyword key={idx}>
                <p>
                  #<span>{keyword}</span>
                </p>
                <Styled.DeleteBtn
                  type="button"
                  onClick={() => handleKeywordDelete(idx)}
                >
                  x
                </Styled.DeleteBtn>
              </Styled.Keyword>
            ))}
            <input
              type="text"
              id="keywords"
              maxLength={20}
              onKeyDown={handleKeyDown}
              onFocus={handleKeywordFocus}
              onBlur={handleKeywordBlur}
            />
            <label htmlFor="keywords"></label>
          </Styled.Keywords>
        </Styled.KeywordBox>
        <StudyFooterBtns>
          <StudyCancle
            onClick={() => {
              navigate("/study");
            }}
          >
            취소
          </StudyCancle>
          <StudyConfirm onClick={FeedPost}>등록</StudyConfirm>
        </StudyFooterBtns>
      </Styled.Inputs>
    </Styled.Wrapper>
  );
};

export default Upload;

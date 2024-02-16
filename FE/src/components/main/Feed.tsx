import { Viewer } from "@toast-ui/react-editor";
import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { HashLink } from "react-router-hash-link";
import Swal from "sweetalert2";
import useDeleteBookmark from "../../apis/feed/useDeleteBookmark";
import useDeleteFeed from "../../apis/feed/useDeleteFeed";
import useDeleteLike from "../../apis/feed/useDeleteLike";
import usePostBookmark from "../../apis/feed/usePostBookmark";
import usePostLike from "../../apis/feed/usePostLike";
import useGetFeedMatch from "../../apis/match/GetFeedMatch";
import basicProfile from "../../assets/icon/basicProfile.png";
import bookmark from "../../assets/icon/bookmark.png";
import bookmarked from "../../assets/icon/bookmarked.png";
import like from "../../assets/icon/like.png";
import liked from "../../assets/icon/liked.png";
import more from "../../assets/icon/more.png";
import reply from "../../assets/icon/reply.png";
import share from "../../assets/icon/share.png";
import { color1 } from "../../constants/colors";
import { DropdownState } from "../../interfaces/DropdownProps";
import { FeedProps } from "../../interfaces/feed/FeedProps";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import Dropdown from "../common/Dropdown";
import { Footer, Header, Keywords, Main, More, Wrapper } from "./FeedStyle";

const Feed = ({ feed }: FeedProps) => {
  const {
    feed_id,
    member,
    title,
    keywords,
    total_time,
    updated_time,
    is_liked,
    is_bookmarked,
  } = feed;
  const data = feed.content;
  const { image_id, nickname, member_id } = member;
  const navigate = useNavigate();
  const pathName = location.pathname.slice(1, 6);
  const deleteFeed = useDeleteFeed();
  const postLike = usePostLike();
  const deleteLike = useDeleteLike();
  const postBookmark = usePostBookmark();
  const deleteBookmark = useDeleteBookmark();
  const getFeedMatch = useGetFeedMatch();
  const accessToken = localStorage.getItem("accessToken");
  const imageURL = `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`;
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  // 뷰어 로직
  const viewerRef = useRef<Viewer>(null);
  const [content, setContent] = useState("");
  useEffect(() => {
    if (!data) return;
    const arrayFormData: [number, [string, string]][] = JSON.parse(data);
    const newContent = arrayFormData.map((data) => data[1][1]).join("\n\n");
    setContent(newContent);
  }, [data]);
  useEffect(() => {
    viewerRef.current?.getInstance().setMarkdown(content);
  }, [content]);

  // 시간 변환
  const updatedTime = (updated_time: Date) => {
    const time = new Date(updated_time);
    const option: Intl.DateTimeFormatOptions = {
      dateStyle: "long",
      timeStyle: "short",
    };
    const result = new Intl.DateTimeFormat("ko-KR", option).format(time);
    return result;
  };
  const totalTime = (total_time: number) => {
    const hour = Math.floor(total_time / 3600);
    const minute = Math.floor((total_time - hour * 3600) / 60);
    const second = Math.floor(total_time % 60);
    return `${hour}h ${minute}m ${second}s`;
  };

  // 드롭다운 로직
  const [state, setState] = useState<DropdownState>({
    position: {
      top: "0px",
      right: "0px",
    },
    size: {
      width: 150,
      height: 50,
    },
    buttons: [
      {
        useFunction: () => {
          if (confirm("피드를 삭제하시겠습니까?")) {
            deleteFeed({ feed_id });
          }
        },
        content: "삭제",
      },
    ],
    isActive: false,
  });
  const children = (
    <More>
      <img src={more} alt="더보기" />
    </More>
  );

  // 공유 버튼 클립보드 복사
  const handleShare = async () => {
    try {
      await navigator.clipboard.writeText(
        `https://fe.wondoo.kr/detail/${feed_id}`
      );
      Swal.fire({
        icon: "success",
        width: "600px",
        title: `클립보드에 피드 주소가 복사되었습니다.`,
        confirmButtonColor: color1,
        confirmButtonText: "확인",
      });
    } catch (e) {
      Swal.fire({
        icon: "error",
        title: "복사 실패하였습니다.",
        confirmButtonColor: color1,
        confirmButtonText: "확인",
      });
    }
  };

  // 좋아요 로직
  const [isLiked, setIsLiked] = useState(false);
  const LikePost = async () => {
    const is_liked = await postLike({ feed_id });
    if (typeof is_liked !== "boolean") return;
    setIsLiked(is_liked);
  };
  const LikeDelete = async () => {
    const is_liked = await deleteLike({ feed_id });
    if (typeof is_liked !== "boolean") return;
    setIsLiked(is_liked);
  };
  useEffect(() => {
    if (!is_liked) return;
    setIsLiked(is_liked);
  }, [is_liked]);

  // 북마크 로직
  const [isBookmarked, setIsBookmarked] = useState(false);
  const BookmarkPost = async () => {
    const is_bookmarked = await postBookmark({ feed_id });
    if (typeof is_bookmarked !== "boolean") return;
    setIsBookmarked(is_bookmarked);
  };
  const BookmarkDelete = async () => {
    const is_bookmarked = await deleteBookmark({ feed_id });
    if (typeof is_bookmarked !== "boolean") return;
    setIsBookmarked(is_bookmarked);
  };
  useEffect(() => {
    if (!is_bookmarked) return;
    setIsBookmarked(is_bookmarked);
  }, [is_bookmarked]);

  // 적합도 로직
  const [match, setMatch] = useState(0);
  useEffect(() => {
    if (!accessToken) return;
    const MatchSet = async () => {
      const result = await getFeedMatch({ feed_id });
      if (typeof result !== "number") return;
      setMatch(result);
    };
    MatchSet();
  }, []);

  return (
    <Wrapper>
      <Header>
        <div>
          <img
            onClick={
              pathName !== "study"
                ? () => navigate(`/member/${member_id}`)
                : undefined
            }
            src={image_id ? imageURL : basicProfile}
            alt="기본 프로필"
          />
        </div>
        <div>
          <span
            onClick={
              pathName !== "study"
                ? () => navigate(`/member/${member_id}`)
                : undefined
            }
          >
            {nickname}
          </span>
        </div>
        {member_id === userId && pathName !== "study" ? (
          <Dropdown state={state} setState={setState} children={children} />
        ) : null}
      </Header>
      <Main>
        <div>
          <h2>{title}</h2>
          <p>{totalTime(total_time)}</p>
          {accessToken ? <span>예상 관심도: {match}%</span> : null}
        </div>
        <Keywords>
          {keywords?.map((keyword, idx) =>
            pathName !== "study" ? (
              <Link key={idx} to={`/search/${keyword}`}>
                #<span>{keyword}</span>
              </Link>
            ) : (
              <div>
                #<span>{keyword}</span>
              </div>
            )
          )}
        </Keywords>
        <Viewer ref={viewerRef} initialValue={content} />
      </Main>
      <Footer>
        <div>{updatedTime(updated_time)}</div>
        <div>
          <div>
            {isLiked ? (
              <img src={liked} alt="좋아요 취소" onClick={LikeDelete} />
            ) : (
              <img
                src={like}
                alt="좋아요"
                onClick={pathName !== "study" ? LikePost : undefined}
              />
            )}
          </div>
          {pathName !== "study" ? (
            <HashLink to={`/detail/${feed_id}#replies`}>
              <img src={reply} alt="댓글보기" />
            </HashLink>
          ) : (
            <div>
              <img src={reply} alt="댓글보기" />
            </div>
          )}
          <div>
            {isBookmarked ? (
              <img
                src={bookmarked}
                alt="북마크 취소"
                onClick={pathName !== "study" ? BookmarkDelete : undefined}
              />
            ) : (
              <img
                src={bookmark}
                alt="북마크"
                onClick={pathName !== "study" ? BookmarkPost : undefined}
              />
            )}
          </div>
          <div>
            <img
              src={share}
              alt="공유"
              onClick={pathName !== "study" ? handleShare : undefined}
            />
          </div>
        </div>
      </Footer>
    </Wrapper>
  );
};

export default Feed;

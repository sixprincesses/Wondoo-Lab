import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/react-editor";
import { LegacyRef, UIEvent, useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { HashLink } from "react-router-hash-link";
import Swal from "sweetalert2";
import useDeleteBookmark from "../../apis/feed/useDeleteBookmark";
import useDeleteLike from "../../apis/feed/useDeleteLike";
import usePostBookmark from "../../apis/feed/usePostBookmark";
import usePostLike from "../../apis/feed/usePostLike";
import basicProfile from "../../assets/icon/basicProfile.png";
import bookmark from "../../assets/icon/bookmark.png";
import bookmarked from "../../assets/icon/bookmarked.png";
import like from "../../assets/icon/like.png";
import liked from "../../assets/icon/liked.png";
import reply from "../../assets/icon/reply.png";
import share from "../../assets/icon/share.png";
import { color1 } from "../../constants/colors";
import { FeedProps } from "../../interfaces/feed/FeedProps";
import {
  FeedBtns,
  Footer,
  Header,
  Keyword,
  Keywords,
  Main,
  Nickname,
  Profile,
  Title,
  ViewerContainer,
  Wrapper,
} from "./FeedCardStyle";

const FeedCard = ({ feed }: FeedProps) => {
  const navigate = useNavigate();
  const postLike = usePostLike();
  const deleteLike = useDeleteLike();
  const postBookmark = usePostBookmark();
  const deleteBookmark = useDeleteBookmark();
  const { feed_id, member, title, keywords, is_liked, is_bookmarked } = feed;
  const data = feed.content;
  const { image_id, nickname, member_id } = member;
  const [imageUrl, setImageUrl] = useState(
    `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
  );

  useEffect(() => {
    setImageUrl(
      `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
    );
  }, [image_id]);

  // 데이터 파싱
  const viewerRef = useRef<Viewer>(null);
  const [content, setContent] = useState("");
  useEffect(() => {
    const arrayFormData: [number, [string, string]][] = JSON.parse(data);
    const newContent = arrayFormData.map((data) => data[1][1]).join("\n\n");
    setContent(newContent);
  }, [data]);
  useEffect(() => {
    viewerRef.current?.getInstance().setMarkdown(content);
  }, [content]);

  // 셰이더 로직
  const maxHeight = 278;
  const container: LegacyRef<HTMLDivElement> = useRef(null);
  const handleShader = (e: UIEvent) => {
    const target = e.target as HTMLDivElement;
    const firstChild = target.firstChild as HTMLDivElement;
    const lastChild = target.lastChild as HTMLDivElement;
    if (target.scrollTop < 1) {
      firstChild.style.display = "none";
    } else {
      firstChild.style.display = "block";
    }
    if (target.scrollTop > target.scrollHeight - target.clientHeight - 1) {
      lastChild.style.display = "none";
    } else {
      lastChild.style.display = "block";
    }
  };

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

  useEffect(() => {
    if (!container.current) return;
    if (!container.current.lastChild) return;
    const lastChild = container.current?.lastChild as HTMLDivElement;
    if (container.current.clientHeight < maxHeight) {
      lastChild.style.display = "none";
    }
  }, [container]);

  return (
    <Wrapper>
      <Profile>
        <img
          src={image_id ? imageUrl : basicProfile}
          alt="프로필 이미지"
          onClick={() => navigate(`/member/${member_id}`)}
        />
        <div />
      </Profile>
      <Main>
        <Header>
          <Nickname onClick={() => navigate(`/member/${member_id}`)}>
            {nickname}
          </Nickname>
        </Header>
        <Title onClick={() => navigate(`/detail/${feed_id}`)}>{title}</Title>
        <ViewerContainer
          maxHeight={maxHeight}
          onClick={() => navigate(`/detail/${feed_id}`)}
        >
          <div ref={container} onScroll={handleShader}>
            <div className="front-shader" />
            <Viewer ref={viewerRef} initialValue={content} />
            <div className="back-shader" />
          </div>
        </ViewerContainer>
      </Main>
      <Footer>
        <Keywords>
          {keywords?.map((keyword, idx) => {
            return (
              <Keyword to={`/search/${keyword}`} key={idx}>
                #<span>{keyword}</span>
              </Keyword>
            );
          })}
        </Keywords>
        <FeedBtns>
          <div>
            {isLiked ? (
              <img src={liked} alt="좋아요 취소" onClick={LikeDelete} />
            ) : (
              <img src={like} alt="좋아요" onClick={LikePost} />
            )}
          </div>
          <HashLink to={`/detail/${feed_id}#replies`}>
            <img src={reply} alt="댓글 보기" />
          </HashLink>
          <div>
            {isBookmarked ? (
              <img
                src={bookmarked}
                alt="북마크 취소"
                onClick={BookmarkDelete}
              />
            ) : (
              <img src={bookmark} alt="북마크" onClick={BookmarkPost} />
            )}
          </div>
          <div>
            <img src={share} alt="공유" onClick={handleShare} />
          </div>
        </FeedBtns>
      </Footer>
    </Wrapper>
  );
};

export default FeedCard;

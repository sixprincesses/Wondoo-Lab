import { ChangeEvent, FormEvent, MouseEvent, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useDeleteComment from "../../apis/comment/useDeleteComment";
import usePutComment from "../../apis/comment/usePutComment";
import basicProfile from "../../assets/icon/basicProfile.png";
import reply from "../../assets/icon/reply.png";
import { comment } from "../../interfaces/feed/FeedState";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import {
  Btns,
  Content,
  ContentBox,
  ContentPut,
  Nickname,
  NicknameBox,
  NoReply,
  ProfileImg,
  Replies,
  ReplyBtn,
  Wrapper,
} from "./CommentStyle";
import Reply from "./Reply";
import ReplyPost from "./ReplyPost";

const Comment = ({ comment }: { comment: comment }) => {
  const { feedId } = useParams();
  const navigate = useNavigate();
  const putComment = usePutComment();
  const deleteComment = useDeleteComment();
  const [content, setContent] = useState("");
  const [is_deleted, setIs_deleted] = useState(false);
  const { comment_id, member, replies } = comment;
  const { member_id, nickname, image_id } = member;
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  // 답글 로직
  const [isOpen, setIsOpen] = useState(false);
  const openReply = () => {
    setIsOpen(true);
  };
  const closeReply = () => {
    setIsOpen(false);
  };

  // 댓글 수정 로직
  const [isPut, setIsPut] = useState(false);
  const [putContent, setPutContent] = useState("");
  const openPut = () => {
    setIsPut(true);
    setPutContent(content);
  };
  const closePut = () => {
    setIsPut(false);
    setPutContent("");
  };
  const handlePutContent = (e: ChangeEvent<HTMLInputElement>) => {
    setPutContent(e.target.value);
  };
  const handlePutComment = async (
    e: FormEvent<HTMLFormElement> | MouseEvent
  ) => {
    e.preventDefault();
    if (confirm("댓글을 수정하시겠습니까?")) {
      const params = {
        comment_id,
        content: putContent,
      };
      const result = await putComment(params);
      if (result) {
        setContent(putContent);
      } else {
        alert("댓글 수정에 실패하였습니다.");
      }
      setIsPut(false);
    }
  };

  // 댓글 삭제 로직
  const handleDeleteComment = async () => {
    if (typeof feedId !== "string") return;
    if (confirm("댓글을 삭제하시겠습니까?")) {
      const params = {
        feed_id: parseInt(feedId),
        comment_id,
      };
      const result = await deleteComment(params);
      if (result) {
        setIs_deleted(true);
      } else {
        alert("댓글 삭제에 실패하였습니다.");
      }
    }
  };
  useEffect(() => {
    if (is_deleted) {
      setContent("삭제된 메세지입니다.");
    }
  }, [is_deleted]);

  useEffect(() => {
    if (!comment.content) return;
    setContent(comment.content);
  }, []);
  useEffect(() => {
    if (!comment.is_deleted) return;
    setIs_deleted(comment.is_deleted);
  }, []);

  return (
    <Wrapper>
      <ProfileImg>
        <img
          onClick={() => navigate(`/member/${member_id}`)}
          src={
            image_id
              ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
              : basicProfile
          }
          alt="프로필 이미지"
        />
      </ProfileImg>
      <NicknameBox>
        <Nickname>
          <span onClick={() => navigate(`/member/${member_id}`)}>
            {nickname}
          </span>
          {/* <div>bedge</div> */}
        </Nickname>
        {member_id === userId && !is_deleted ? (
          <Btns>
            {isPut ? (
              <>
                <button className="colorR" onClick={closePut}>
                  취소
                </button>
                <button className="colorB" onClick={handlePutComment}>
                  완료
                </button>
              </>
            ) : (
              <>
                <button className="colorB" onClick={openPut}>
                  수정
                </button>
                <button className="colorR" onClick={handleDeleteComment}>
                  삭제
                </button>
              </>
            )}
          </Btns>
        ) : null}
      </NicknameBox>
      <ContentBox>
        {isPut ? (
          <ContentPut onSubmit={handlePutComment}>
            <input
              id="content-put"
              name="content"
              value={putContent}
              onChange={handlePutContent}
            />
            <button></button>
          </ContentPut>
        ) : (
          <Content>{content}</Content>
        )}
        {isOpen ? (
          <div>
            <ReplyBtn onClick={closeReply}>
              <img src={reply} alt="답글 닫기" />
              <span>답글 닫기</span>
            </ReplyBtn>
            <ReplyPost parentId={comment_id} />
            <Replies>
              {replies?.length ? (
                replies.map((reply) => (
                  <Reply key={reply.comment_id} reply={reply} />
                ))
              ) : (
                <NoReply>답글이 없습니다.</NoReply>
              )}
            </Replies>
          </div>
        ) : (
          <ReplyBtn onClick={openReply}>
            <img src={reply} alt="답글" />
            <span>답글</span>
          </ReplyBtn>
        )}
      </ContentBox>
    </Wrapper>
  );
};

export default Comment;

import { ChangeEvent, FormEvent, MouseEvent, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useDeleteComment from "../../apis/comment/useDeleteComment";
import usePutComment from "../../apis/comment/usePutComment";
import basicProfile from "../../assets/icon/basicProfile.png";
import { reply } from "../../interfaces/feed/FeedState";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { Btns, Nickname, NicknameBox } from "./CommentStyle";
import { Content, ContentPut, Profile, Wrapper } from "./ReplyStyle";

const Reply = ({ reply }: { reply: reply }) => {
  const { feedId } = useParams();
  const putComment = usePutComment();
  const deleteComment = useDeleteComment();
  const [content, setContent] = useState("");
  const [is_deleted, setIs_deleted] = useState(false);
  const { comment_id, member } = reply;
  const { nickname, image_id, member_id } = member;
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  // 답글 수정 로직
  const [isPut, setIsPut] = useState(false);
  const [putContent, setPutContent] = useState(content);
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
    if (confirm("답글을 수정하시겠습니까?")) {
      const params = {
        comment_id,
        content: putContent,
      };
      const result = await putComment(params);
      if (result) {
        setContent(putContent);
      } else {
        alert("답글 수정에 실패하였습니다.");
      }
      setIsPut(false);
    }
  };

  // 답글 삭제 로직
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
    if (!reply.content) return;
    setContent(reply.content);
  }, []);
  useEffect(() => {
    if (!reply.is_deleted) return;
    setIs_deleted(reply.is_deleted);
  }, []);

  return (
    <Wrapper>
      <Profile>
        <img
          src={
            image_id
              ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
              : basicProfile
          }
          alt="프로필"
        />
      </Profile>
      <div>
        <NicknameBox>
          <Nickname>
            <span>{nickname}</span>
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
        {isPut ? (
          <ContentPut onSubmit={handlePutComment}>
            <input
              id="reply-put"
              name="reply"
              value={putContent}
              onChange={handlePutContent}
            />
            <button></button>
          </ContentPut>
        ) : (
          <Content>{content}</Content>
        )}
      </div>
    </Wrapper>
  );
};

export default Reply;

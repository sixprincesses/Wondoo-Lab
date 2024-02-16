import { ChangeEvent, FormEvent, useState } from "react";
import { useParams } from "react-router-dom";
import usePostComment from "../../apis/comment/usePostComment";
import basicProfile from "../../assets/icon/basicProfile.png";
import { PostCommentParams } from "../../interfaces/comment/PostCommentParams";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { Button, Form, Input, Wrapper } from "./CommentPostStyle";

const CommentPost = () => {
  const { feedId } = useParams();
  const postComment = usePostComment();
  const user = useAppSelector((state: RootState) => state.user.userInfo);
  const { image_id } = user;

  const [input, setInput] = useState("");

  const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
    setInput(e.target.value);
  };
  const submitComment = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (input === "") return;
    if (typeof feedId !== "string") return;
    const params: PostCommentParams = {
      feed_id: parseInt(feedId),
      content: input,
    };
    postComment(params);
    setInput("");
  };

  return (
    <Wrapper id="replies">
      <div>
        <img
          src={
            image_id
              ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
              : basicProfile
          }
          alt="기본 프로필"
        />
        <Form onSubmit={submitComment}>
          <Input>
            <input
              className="input"
              name="reply"
              placeholder="댓글을 입력해주세요."
              value={input}
              onChange={handleInput}
            />
            <label className="label" htmlFor="reply"></label>
          </Input>
          <Button>등록</Button>
        </Form>
      </div>
    </Wrapper>
  );
};

export default CommentPost;

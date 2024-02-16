import { ChangeEvent, FormEvent, useState } from "react";
import { useParams } from "react-router-dom";
import usePostComment from "../../apis/comment/usePostComment";
import { color1, colorNG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  border: 1px solid ${color1};
  border-bottom: none;
  border-radius: 10px 10px 0 0;
  padding: 5px;
  margin-top: 10px;
`;

const Form = styled.form`
  width: 100%;
  display: flex;
  gap: 10px;
`;

const Input = styled.input`
  flex: 1;
  border: none;
  border-radius: 5px;
  outline: none;
  background: ${colorNG};
  padding: 5px;
`;

const Btn = styled.button`
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  background: ${color1};
`;

const ReplyPost = ({ parentId }: { parentId: number }) => {
  const { feedId } = useParams();
  const postComment = usePostComment();

  // 답글 포스팅 로직
  const [input, setInput] = useState("");
  const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
    setInput(e.target.value);
  };
  const submitInput = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (input === "") return;
    if (typeof feedId !== "string") return;
    const params = {
      feed_id: parseInt(feedId),
      parent_id: parentId,
      content: input,
    };
    postComment(params);
    setInput("");
  };

  return (
    <Wrapper>
      <Form onSubmit={submitInput}>
        <Input
          placeholder="답글을 입력하세요"
          value={input}
          onChange={handleInput}
        />
        <Btn>등록</Btn>
      </Form>
    </Wrapper>
  );
};

export default ReplyPost;

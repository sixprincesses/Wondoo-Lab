import { color1, colorB, colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 50px 526px;
  grid-template-rows: 50px 1fr;
  gap: 10px;
  & > div:nth-of-type(2) {
  }
`;

const ProfileImg = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  & > img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
`;

const NicknameBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Nickname = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
  & > span {
    font-weight: 600;
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
`;

const Btns = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  & button {
    border: none;
    font-size: 12px;
    font-weight: 600;
    background: transparent;
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
  & .colorB {
    color: ${colorB};
  }
  & .colorR {
    color: tomato;
  }
`;

const ContentBox = styled.div`
  width: 100%;
  grid-column: 2/3;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const Content = styled.div`
  width: 100%;
  word-wrap: break-word;
  background: ${color1};
  border-radius: 10px;
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
`;

const ContentPut = styled.form`
  width: 100%;
  background: ${colorG};
  border-radius: 10px;
  padding: 10px;
  & > input {
    width: 100%;
    height: 100%;
  }
  & > button {
    display: none;
  }
`;

const ReplyBtn = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
  margin-left: 10px;
  cursor: pointer;
  & > img {
    height: 22px;
  }
`;

const Replies = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid ${color1};
  border-top: none;
  border-radius: 0 0 10px 10px;
  padding: 10px;
`;

const NoReply = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
`;

export {
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
};

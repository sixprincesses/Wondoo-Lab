import { color1, colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  height: calc(100%);
  min-width: 400px;
  display: flex;
  flex-direction: column;
  justify-content: end;
`;

const Chats = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  padding-bottom: 0;
  overflow-y: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  & > .self {
    flex-direction: row-reverse;
  }
`;

const Chat = styled.div`
  width: 100%;
  display: flex;
  align-items: end;
  gap: 10px;
  & > div:nth-of-type(1) {
    display: flex;
    align-items: center;
    justify-content: center;
    & > img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      object-fit: cover;
    }
  }
`;

const ChatMessage = styled.div`
  background-color: ${color1};
  padding: 5px 10px;
  border-radius: 5px;
`;

const ChatTime = styled.div`
  display: flex;
  justify-content: flex-end;
  font-size: 8px;
`;

const ToBottomBtn = styled.div`
  position: absolute;
  top: 10px;
  right: calc(50% - 50px);
  width: 100px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  background: ${colorG};
  box-shadow: 1px 1px 1px ${colorG};
  cursor: pointer;
`;

const NoChats = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export { Chat, ChatMessage, ChatTime, Chats, NoChats, ToBottomBtn, Wrapper };

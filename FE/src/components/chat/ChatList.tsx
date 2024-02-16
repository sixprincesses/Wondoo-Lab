import { forwardRef } from "react";
import { useSelector } from "react-redux";
import basicProfile from "../../assets/icon/basicProfile.png";
import { RootState } from "../../store/store";
import {
  Chat,
  ChatMessage,
  ChatTime,
  Chats,
  NoChats,
  ToBottomBtn,
  Wrapper,
} from "./ChatListStyle";

interface ChatListProps {
  isReceive: boolean;
  checkReceive: () => void;
}

const ChatList = forwardRef<HTMLDivElement, ChatListProps>((props, ref) => {
  // 채팅 불러오기
  const messages = useSelector((state: RootState) => state.chat.messages);
  const member_id = useSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  // 채팅 시간 파싱 로직
  const time = (createdTime: Date) => {
    const chatTime = new Date(createdTime);
    const option: Intl.DateTimeFormatOptions = {
      timeStyle: "short",
    };
    const time = new Intl.DateTimeFormat("ko-KR", option).format(chatTime);
    return time;
  };

  return (
    <Wrapper>
      {messages?.length ? (
        <Chats ref={ref}>
          {messages.map((message, idx) => (
            <Chat
              key={idx}
              className={message.member_id === member_id ? "self" : ""}
            >
              <div>
                <img
                  src={
                    message.image_id
                      ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${message.image_id}`
                      : basicProfile
                  }
                  alt="프로필 이미지"
                />
              </div>
              <ChatMessage>{message.content}</ChatMessage>
              <ChatTime>{time(message.createdTime)}</ChatTime>
            </Chat>
          ))}
          {props.isReceive ? (
            <ToBottomBtn onClick={props.checkReceive}>
              새로운 메세지
            </ToBottomBtn>
          ) : null}
        </Chats>
      ) : (
        <NoChats>채팅 기록이 없습니다.</NoChats>
      )}
    </Wrapper>
  );
});

export default ChatList;

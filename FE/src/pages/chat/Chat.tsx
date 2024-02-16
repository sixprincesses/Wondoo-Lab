import { ChangeEvent, FormEvent, useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router";
import { stompClient } from "../../apis/websocket/client";
import ChatList from "../../components/chat/ChatList";
import LiveCode from "../../components/chat/LiveCode";
import { RootState } from "../../store/store";
import { Form, Selected, Unselected, Wrapper } from "./ChatStyle";

const Chat = () => {
  const { channelRef } = useParams();
  const chatsRef = useRef<HTMLDivElement>(null);
  const userId = useSelector(
    (state: RootState) => state.user.userInfo.member_id
  );
  const messages = useSelector((state: RootState) => state.chat.messages);

  // 메세지 송신 로직
  const [message, setMessage] = useState("");
  const changeMessage = (e: ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value);
  };
  const submitMessage = (e: FormEvent) => {
    e.preventDefault();
    if (message === "") return;
    if (!stompClient) {
      console.log("클라이언트 없음");
      return;
    }
    if (stompClient.ws.readyState !== WebSocket.OPEN) {
      console.log("웹소켓 연결 안됨");
      location.reload();
      return;
    }
    const chatMessage = {
      content: message,
      sender_id: userId,
      message_type: "MESSAGE_TXT",
      reference: channelRef,
    };
    stompClient.send(
      "/server/message/" + channelRef,
      {},
      JSON.stringify(chatMessage)
    );
    setMessage("");

    // 스크롤 내리기 로직
    if (!chatsRef.current) return;
    const scrollHeight = chatsRef.current.scrollHeight;
    chatsRef.current.scrollTop = scrollHeight;
  };

  // 채팅 업데이트 되면 내려가기 버튼 팝업 로직
  const [isReceive, setIsReceive] = useState(false);
  const checkReceive = () => {
    if (!chatsRef.current) return;
    chatsRef.current.scrollTop = chatsRef.current.scrollHeight;
    setIsReceive(false);
  };
  useEffect(() => {
    if (!chatsRef.current) return;
    const scrollTop = chatsRef.current.scrollTop;
    const scrollHeight = chatsRef.current.scrollHeight;
    console.log("현재 스크롤:" + scrollTop, scrollHeight);
    if (scrollTop === scrollHeight) return;
    setIsReceive(true);
  }, [messages]);

  // 라이브 코드
  const [live, setLive] = useState(false);
  const handleLiveCode = () => {
    setLive(!live);
  };

  return (
    <Wrapper>
      {channelRef ? (
        <>
          <Selected live={live}>
            <button type="button" onClick={handleLiveCode}>
              Live
            </button>
            {live ? <LiveCode /> : null}
            <ChatList
              ref={chatsRef}
              isReceive={isReceive}
              checkReceive={checkReceive}
            />
          </Selected>
          <Form onSubmit={submitMessage}>
            <input
              value={message}
              onChange={changeMessage}
              placeholder="메세지를 입력하세요."
              disabled={channelRef ? false : true}
            />
            <button>보내기</button>
          </Form>
        </>
      ) : (
        <Unselected>채팅방을 선택해주세요!</Unselected>
      )}
    </Wrapper>
  );
};

export default Chat;

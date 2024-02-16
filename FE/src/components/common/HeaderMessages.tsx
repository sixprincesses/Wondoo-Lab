import { useState, useRef } from "react";
import * as Styled from "./HeaderMessageStyle"
import { MessageProps } from "../../interfaces/alarm/HeaderAlarm";
import FetchMoreMessages from "../../apis/alarm/FetchMoreMessages.tsx";
import ReadAllMessages from "../../apis/alarm/ReadAllMessages.tsx"
import ReadMessage from "../../apis/alarm/ReadMessage.tsx"
import createMessage from "../alarm/CreateMessage.tsx";
import ChangeURL from "../alarm/ChangeURL.tsx";
import { ContentObject } from "../../interfaces/alarm/HeaderAlarm";
import { useNavigate } from "react-router-dom";

const Messages = ({ setMessageNum, showMessages, allMessages, setAllMessages }: MessageProps) => {
  const [page, setPage] = useState<number>(1)
  const [size, setSize] = useState<number>(5)
  const containerRef = useRef<HTMLDivElement>(null);
  const access_token = localStorage.getItem("accessToken");
  const navigate = useNavigate();

  const handleMore = async () => {
    const newMessages = await FetchMoreMessages(page, size, access_token)
    const newCreatedMessages = newMessages.map((message: ContentObject) => createMessage(message));
    if (newMessages.length > 0) {
      setAllMessages((prevMessages) => [...prevMessages, ...newCreatedMessages]);
      setSize((prevSize) => prevSize + 1);
      setPage((prevPage) => prevPage + 1);
    } 
  };

  const markAllAsRead = async () => {
    try {
      ReadAllMessages(access_token);
      const updatedMessages = allMessages.map((message) => ({
        ...message,
        read: true,
      }));
      setAllMessages(updatedMessages);
      setMessageNum(0)
    } catch (error) {
      console.log(error)
    }
  };

  const markAsRead = async (id: string) => {
    try {
      const unread_num = await ReadMessage(id, access_token)
      const updatedMessages = allMessages.map((message) =>
        message.id === id ? { ...message, read: true } : message
      );
      setAllMessages(updatedMessages);
      setMessageNum(unread_num)
    } catch (error) {
      console.error('Error marking message as read:', error);
    }
  };

  return (
    <Styled.MessagesWrapper ref={containerRef} showMessages={showMessages}>
      <Styled.MessageScroll>
        <Styled.MessageHeader>
          <Styled.HeaderTitle>알림</Styled.HeaderTitle>
          <Styled.ReadAllHeading onClick={markAllAsRead}>모두 읽기</Styled.ReadAllHeading>
        </Styled.MessageHeader>
        <Styled.MessageList>
          {allMessages.map((message, index) => (
            <Styled.MessageItem key={index} read={message.read} onClick={() =>{ markAsRead(message.id); ChangeURL(message, navigate); }}>
              {message.content}
            </Styled.MessageItem>        
          ))}
        </Styled.MessageList>
        <Styled.WatchMore onClick={handleMore}>더 보기</Styled.WatchMore>
      </Styled.MessageScroll>
    </Styled.MessagesWrapper>
  );
};

export default Messages;
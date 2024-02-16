import { Frame, Message } from "stompjs";
import { channelReceivePayload } from "../../interfaces/chat/ChannelReceivePayload";
import { messageReceivePayload } from "../../interfaces/chat/MessageReceivePayload";
import { setChannels, setMessages } from "../../store/chatSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { stompClient } from "./client";

const useConnect = () => {
  const dispatch = useAppDispatch();
  const member_id = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );
  const accessToken = localStorage.getItem("accessToken");

  const onMessageReceived = (payload: Message) => {
    const payloadData: messageReceivePayload = JSON.parse(payload.body);
    dispatch(setMessages(payloadData.body.response.messages));
  };

  const onChannelReceived = (payload: Message) => {
    const payloadData: channelReceivePayload = JSON.parse(payload.body);
    const channels = payloadData.body.response.channels;

    dispatch(setChannels(channels));
    channels.map((channel) => {
      stompClient?.subscribe(
        "/server/channel/" + channel.reference,
        onMessageReceived,
        { id: member_id }
      );
    });
  };

  const onConnected = (member_id: number) => {
    console.log("member_id: " + member_id);
    // 기본 채널 구독
    stompClient?.subscribe(
      "/server/channel/connection/" + member_id,
      onChannelReceived
    );
  };

  const onError = (err: Frame | string) => {
    console.error("Chatting socket connetion error: ", err);
  };

  const connect = (member_id: number) => {
    stompClient?.connect(
      {
        login: accessToken,
        passcode: accessToken,
      },
      () => onConnected(member_id),
      onError
    );
  };

  return connect;
};

export default useConnect;

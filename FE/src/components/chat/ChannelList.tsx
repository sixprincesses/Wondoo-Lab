import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router";
import useGetChats from "../../apis/websocket/useGetChats";
import basicProfile from "../../assets/icon/basicProfile.png";
import { channel } from "../../interfaces/chat/ChannelReceivePayload";
import { message } from "../../interfaces/chat/MessageReceivePayload";
import { setPrevMessages, setSelectedChannelName } from "../../store/chatSlice";
import { useAppDispatch } from "../../store/hooks";
import { RootState } from "../../store/store";
import { Channel, NoChannel, Wrapper } from "./ChannelListStyle";

const ChannelList = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const getChats = useGetChats();
  const { channelRef } = useParams();

  // 채널 로딩 로직
  const channels: channel[] = useSelector(
    (state: RootState) => state.chat.channels
  );
  const lastMsgStr: string = useSelector(
    (state: RootState) => state.chat.lastMessages
  );
  const lastMsgs: Map<string, message> = new Map(JSON.parse(lastMsgStr));

  // 채널 변경 로직
  const toChannel = (reference: string) => {
    console.log(channelRef, reference);

    if (typeof channelRef === "string" && channelRef === reference) {
      navigate("/chat");
      dispatch(setSelectedChannelName(""));
      dispatch(setPrevMessages([]));
    } else {
      navigate(`/chat/${reference}`);
      dispatch(setSelectedChannelName(reference));
      getChats(reference);
    }
  };

  // 마지막 채팅 관련 로직
  const lastContent = (reference: string) => {
    const lastMsg = lastMsgs.get(reference);
    if (!lastMsg) return "";
    return lastMsg?.content;
  };
  const lastTime = (reference: string) => {
    const lastMsg = lastMsgs.get(reference);

    if (!lastMsg) return "";
    const lastTime = new Date(lastMsg.createdTime);
    const option: Intl.DateTimeFormatOptions = {
      timeStyle: "short",
    };
    const time = new Intl.DateTimeFormat("ko-KR", option).format(lastTime);
    return time.toString();
  };

  return (
    <Wrapper>
      {channels?.length ? (
        channels.map((channel) => (
          <Channel
            key={channel.reference}
            onClick={() => toChannel(channel.reference)}
            className={channelRef === channel.reference ? "active" : ""}
          >
            <div>
              <img
                src={channel.image_id ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${channel.image_id}` : basicProfile}
                alt="채팅창 이미지"
              />
              <span>{channel.name}</span>
            </div>
            <div>
              <div>{lastContent(channel.reference)}</div>
              <div>{lastTime(channel.reference)}</div>
            </div>
          </Channel>
        ))
      ) : (
        <NoChannel>진행중인 채팅이 없습니다.</NoChannel>
      )}
    </Wrapper>
  );
};

export default ChannelList;

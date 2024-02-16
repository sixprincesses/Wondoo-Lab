import { MouseEvent } from "react";
import { useSelector } from "react-redux";
import { setTab } from "../../store/chatSlice";
import { useAppDispatch } from "../../store/hooks";
import { RootState } from "../../store/store";
import ChannelList from "./ChannelList";
import { Tab, Wrapper } from "./ChatTabStyle";
import FriendList from "./FriendList";

const ChatTab = () => {
  const dispatch = useAppDispatch();

  // 탭 변경 로직
  const tab = useSelector((state: RootState) => state.chat.tab);
  const handleTab = (e: MouseEvent<HTMLButtonElement>) => {
    const target = e.target as HTMLButtonElement;
    dispatch(setTab(target.name));
  };

  return (
    <Wrapper>
      <Tab>
        <button
          onClick={handleTab}
          type="button"
          name="chat"
          className={tab === "chat" ? "active" : ""}
        >
          채팅
        </button>
        <button
          onClick={handleTab}
          type="button"
          name="friend"
          className={tab === "friend" ? "active" : ""}
        >
          친구
        </button>
      </Tab>
      {tab === "chat" ? <ChannelList /> : <FriendList />}
    </Wrapper>
  );
};

export default ChatTab;

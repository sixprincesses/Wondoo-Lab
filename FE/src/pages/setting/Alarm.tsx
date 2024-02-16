import { useState } from "react";
import ToggleButton from "../../components/common/ToggleButton";
import {
  DivSettingAlarmText,
  DivSettingContainer,
  DivSettingInput,
} from "../../components/setting/SettingPageStyle";

const Alarm = () => {
  const [noticeAlarm, setNoticeAlarm] = useState(true);
  const [feedAlarm, setFeedAlarm] = useState(true);
  const [chatAlarm, setChatAlarm] = useState(true);
  const [followAlarm, setFollowAlarm] = useState(true);

  const handleNoticeAlarm = () => {
    setNoticeAlarm(!noticeAlarm);
  };

  const handleFeedAlarm = () => {
    setFeedAlarm(!feedAlarm);
  };

  const handleChatAlarm = () => {
    setChatAlarm(!chatAlarm);
  };

  const handleFollowAlarm = () => {
    setFollowAlarm(!followAlarm);
  };
  return (
    <DivSettingContainer>
      <div className="frame"></div>
      <div>
        <h1 className="title">알림 설정</h1>
        <DivSettingInput height={"90px"}>
          <DivSettingAlarmText>
            <h2>공지사항</h2>
            <span>
              중요한 공지사항, 기능 업데이트 등 원두의 소식을 받을 수 있습니다.
            </span>
          </DivSettingAlarmText>
          <ToggleButton
            width={"80px"}
            height={"40px"}
            isToggleOn={noticeAlarm}
            handleClick={handleNoticeAlarm}
          />
        </DivSettingInput>
        <DivSettingInput height={"90px"}>
          <DivSettingAlarmText>
            <h2>피드</h2>
            <span>
              작성하신 피드의 좋아요, 댓글 등의 변경사항을 알림으로 받을 수
              있습니다.
            </span>
          </DivSettingAlarmText>
          <ToggleButton
            width={"80px"}
            height={"40px"}
            isToggleOn={feedAlarm}
            handleClick={handleFeedAlarm}
          />
        </DivSettingInput>
        <DivSettingInput height={"90px"}>
          <DivSettingAlarmText>
            <h2>채팅</h2>
            <span>다른 회원이 보내는 채팅을 알람으로 받을 수 있습니다.</span>
          </DivSettingAlarmText>
          <ToggleButton
            width={"80px"}
            height={"40px"}
            isToggleOn={chatAlarm}
            handleClick={handleChatAlarm}
          />
        </DivSettingInput>
        <DivSettingInput height={"90px"}>
          <DivSettingAlarmText>
            <h2>팔로우</h2>
            <span>팔로우와 관련된 알림을 받을 수 있습니다. </span>
          </DivSettingAlarmText>
          <ToggleButton
            width={"80px"}
            height={"40px"}
            isToggleOn={followAlarm}
            handleClick={handleFollowAlarm}
          />
        </DivSettingInput>
      </div>
      <div className="frame"></div>
    </DivSettingContainer>
  );
};

export default Alarm;

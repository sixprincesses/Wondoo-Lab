import { useState } from "react";
import { useSubscribeNew } from "../../apis/websocket/useSubscribeNew";
import basicProfile from "../../assets/icon/basicProfile.png";
import online from "../../assets/icon/online.png";
import { DropdownState } from "../../interfaces/DropdownProps";
import { content } from "../../interfaces/member/FollowState";
import Dropdown from "../common/Dropdown";
import { Nickname, Profile, UserState, Wrapper } from "./FriendStyle";

const Friend = ({ member }: { member: content }) => {
  const { member_id, image_id, nickname } = member;
  const subscribeNew = useSubscribeNew();

  // 드롭다운 로직
  const buttons = [
    {
      useFunction: () => {
        subscribeNew(member_id);
      },
      content: "채팅하기",
    },
  ];
  const [state, setState] = useState<DropdownState>({
    position: {
      top: "-1px",
      right: "-121px",
    },
    size: {
      width: 100,
      height: 50,
    },
    buttons: buttons,
    isActive: false,
  });
  const children = (
    <>
      <Profile>
        <img src={image_id ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}` : basicProfile} alt="프로필 이미지" />
      </Profile>
      <Nickname>{nickname}</Nickname>
      <UserState>
        온라인 <img src={online} alt="온라인" />
      </UserState>
    </>
  );

  return (
    <Wrapper>
      <Dropdown state={state} setState={setState} children={children} />
    </Wrapper>
  );
};

export default Friend;

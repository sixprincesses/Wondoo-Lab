import { useNavigate } from "react-router-dom";
import { useSubscribeNew } from "../../apis/websocket/useSubscribeNew";
import basicProfile from "../../assets/icon/basicProfile.png";
import beanImage from "../../assets/icon/bean.png";
import coffeeImage from "../../assets/icon/coffee.png";
import grinderImage from "../../assets/icon/grinder.png";
import wondooImage from "../../assets/icon/wondoo.png";
import { rank } from "../../interfaces/feed/FeedState";
import { Chat, Nickname, Profile, Wrapper, NicknameBox } from "./SidebarUserRankMemberStyle";

const SidebarUserRankMember = ({ member }: { member: rank }) => {
  const navigate = useNavigate();
  const subscribeNew = useSubscribeNew();
  const { nickname, image_id, member_id, score, level } = member;
  const imageURL = `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`;

  let gradeImage = "";
  switch (level.split("_")[0]) {
    case "BEAN":
      gradeImage = beanImage;
      break;
    case "GRINDER":
      gradeImage = grinderImage;
      break;
    case "COFFEE":
      gradeImage = coffeeImage;
      break;
    default:
      gradeImage = wondooImage;
      break;
  }

  return (
    <Wrapper>
      <Profile>
        <img
          onClick={() => navigate(`/member/${member_id}`)}
          src={image_id ? imageURL : basicProfile}
          alt="프로필 이미지"
        />
      </Profile>
      <Nickname>
        <NicknameBox>
          <span onClick={() => navigate(`/member/${member_id}`)}>
            {nickname}
          </span>
          <img
            className="grade-image"
            src={gradeImage}
            alt="유저 등급"
            width="20px"
          />
        </NicknameBox>
        <p>Weekly Score <strong>{score}</strong></p>
      </Nickname>
      <Chat onClick={() => subscribeNew(member_id)}>Chat</Chat>
    </Wrapper>
  );
};

export default SidebarUserRankMember;

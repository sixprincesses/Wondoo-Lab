import { useNavigate } from "react-router-dom";
import basicProfile from "../../assets/icon/basicProfile.png";
import { content } from "../../interfaces/member/FollowState";
import * as Styled from "./FollowStyle.tsx";

interface FollowMember {
  member: content;
}

const FollowMember = ({ member }: FollowMember) => {
  const navigate = useNavigate();
  const imageURL = `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${member.image_id}`;

  return (
    <Styled.FollowMember onClick={() => navigate(`/member/${member.member_id}`)}>
      <img
        src={member.image_id ? imageURL : basicProfile}
        alt=""
        width="40px"
      />
      <span>{member.nickname}</span>
    </Styled.FollowMember>
  );
};

export default FollowMember;

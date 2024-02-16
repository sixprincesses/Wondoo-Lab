import { useNavigate } from "react-router-dom";
import basicProfile from "../../assets/icon/basicProfile.png";
import { bookmark } from "../../interfaces/feed/Bookmark";
import { Content, Profile, Wrapper } from "./UserBookmarkStyle";

interface UserBookmarkProps {
  bookmark: bookmark;
}

const UserBookmark = ({ bookmark }: UserBookmarkProps) => {
  const { member, title, feed_id } = bookmark;
  const { image_id, nickname, member_id } = member;
  const imageUrl = `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`;
  const navigate = useNavigate();

  return (
    <Wrapper>
      <Profile onClick={() => navigate(`/member/${member_id}`)}>
        <img src={image_id ? imageUrl : basicProfile} alt="프로필 이미지" />
      </Profile>
      <Content>
        <p onClick={() => navigate(`/member/${member_id}`)}>{nickname}</p>
        <h3 onClick={() => navigate(`/detail/${feed_id}`)}>{title}</h3>
      </Content>
    </Wrapper>
  );
};

export default UserBookmark;

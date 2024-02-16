import { useState, useEffect, ChangeEvent  } from "react";
import { useNavigate } from "react-router-dom";
import useFollow from "../../apis/member/useFollow";
import useUnfollow from "../../apis/member/useUnfollow";
import { useSubscribeNew } from "../../apis/websocket/useSubscribeNew";
import basicProfile from "../../assets/icon/basicProfile.png";
import dateIcon from "../../assets/icon/date.svg";
import profileBackground from "../../assets/img/Placeholder.png";
import coffeeImage from "../../assets/icon/coffee.png";
import grinderImage from "../../assets/icon/grinder.png";
import beanImage from "../../assets/icon/bean.png";
import wondooImage from "../../assets/icon/wondoo.png";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import useChangeBackground from "../../apis/member/useChangeBackground.tsx"
import useChangeProfileImg from "../../apis/member/useChangeProfileImg.tsx"
import {
  DivImageContainer,
  DivProfileInfoContainer,
  DivUserProfileContainer,
  ProfileBackInput,
  CustomInputLabel
} from "./UserProfileStyle";

const UserProfile = () => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [selectedBack, setSelectedBack] = useState<File | null>(null);
  const [isCoverChangeOpen, setIsCoverChangeOpen] = useState(false);
  const changeBack = useChangeBackground();
  const changeProf = useChangeProfileImg();
  const navigate = useNavigate();
  const follow = useFollow();
  const unfollow = useUnfollow();
  const subscribeNew = useSubscribeNew();
  const user_id = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );
  const member_id = useAppSelector(
    (state: RootState) => state.member.memberInfo.member_id
  );
  const image_id = useAppSelector(
    (state: RootState) => state.member.memberInfo.image_id
  );
  const back_id = useAppSelector(
    (state: RootState) => state.member.memberInfo.cover_image_id
  )
  const nickname = useAppSelector(
    (state: RootState) => state.member.memberInfo.nickname
  );
  const email = useAppSelector(
    (state: RootState) => state.member.memberInfo.email
  );
  const created_time = useAppSelector(
    (state: RootState) => state.member.memberInfo.created_time
  );
  const is_follow = useAppSelector(
    (state: RootState) => state.member.memberInfo.is_follow
  );
  const follower_count = useAppSelector(
    (state: RootState) => state.member.memberInfo.follower_count
  );
  const following_count = useAppSelector(
    (state: RootState) => state.member.memberInfo.following_count
  );
  const level = useAppSelector(
    (state: RootState) => state.member.memberInfo.level
  );

  const [imageUrl, setImageUrl] = useState(`https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`)
  const [backUrl, setBackUrl] = useState(`https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${back_id}`)

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

  useEffect(() => {
    setBackUrl(`https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${back_id}`)
  }, [back_id])

  useEffect(() => {
    setImageUrl(`https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`)
  }, [image_id])

  useEffect(() => {
    if (selectedBack) {
      const formData = new FormData();
      formData.append('image_id', selectedBack);
      // console.log('try to upload the cover', formData, selectedBack)
      changeBack(formData);
    }
  }, [selectedBack])

  useEffect(() => {
    if (selectedFile) {
      const formData = new FormData();
      formData.append('image_id', selectedFile);
      // console.log('try to upload the image', formData, selectedFile)
      changeProf(formData);
    }
  }, [selectedFile])

  const parsedTime = (created_time: Date) => {
    const time = new Date(created_time);
    const option: Intl.DateTimeFormatOptions = {
      dateStyle: "short",
      timeStyle: "short",
    };
    const result = new Intl.DateTimeFormat("ko-KR", option).format(time);
    return result;
  };

  const handleBackChange = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    setSelectedBack(file || null);
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    setSelectedFile(file || null);
  };

  return (
    <DivUserProfileContainer
      onMouseEnter={() => setIsCoverChangeOpen(true)}
      onMouseLeave={() => setIsCoverChangeOpen(false)}
    >
      <DivImageContainer>
        <img
          className="profile-background-image"
          src={back_id ? backUrl : profileBackground}
          alt="프로필 배경 이미지"
          max-height="270px"
        />
        <img
          className="profile-image"
          src={image_id ? imageUrl : basicProfile}
          alt="프로필 이미지"
        />
        {(user_id === member_id) && <input
          type="file"
          className="profile-input"
          onChange={handleFileChange}
          accept="image/*"
        />}
        {(user_id === member_id) && <div className="tip"></div>}
      </DivImageContainer>
      {(user_id === member_id) && <ProfileBackInput
          type="file"
          className="profile-background-input"
          onChange={handleBackChange}
          accept="image/*"
        />}
        {(user_id === member_id) && isCoverChangeOpen && <CustomInputLabel>커버 변경</CustomInputLabel>}
      <DivProfileInfoContainer>
        <div className="nickname-row">
          <div>
            <h2>{nickname}</h2>
            <img className="grade-image" src={gradeImage} alt="유저 등급" width="60px" />
          </div>
          {user_id === member_id ? null : (
            <div>
              <button onClick={() => subscribeNew(member_id)}>Chat</button>
              {is_follow ? (
                <button onClick={() => unfollow(member_id)}>Unfollow</button>
              ) : (
                <button onClick={() => follow(member_id)}>Follow</button>
              )}
            </div>
          )}
        </div>
        <p className="email">{email}</p>
        <div className="location-date">
          <div className="date">
            <img src={dateIcon} />
            Joined {parsedTime(created_time)}
          </div>
        </div>
        <div className="follow">
          <strong>{following_count}{" "}</strong>
          <span onClick={() => navigate(`/follow/${member_id}/followings`)}>
            Following
          </span>
          <strong>{follower_count}{" "}</strong>
          <span onClick={() => navigate(`/follow/${member_id}/followers`)}>
            Followers
          </span>
        </div>
      </DivProfileInfoContainer>
    </DivUserProfileContainer>
  );
};

export default UserProfile;

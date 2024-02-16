import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import basicProfile from "../../assets/icon/basicProfile.png";
import { Wrapper } from "./FeedPostStyle";
import { RootState } from "../../store/store";
import { useAppSelector } from "../../store/hooks";

const FeedPost = () => {
  // const user = store...
  const navigate = useNavigate();
  const image_id = useAppSelector((state: RootState) => state.user.userInfo.image_id)
  const [imageUrl, setImageUrl] = useState<string>(`https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`)

  useEffect(() => {
    setImageUrl(`https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`)
  }, [image_id])

  const movetoMy = () => {
    
  }

  return (
    <Wrapper>
      <div>
        <img src={image_id? imageUrl : basicProfile} alt="기본 프로필" className="image" onClick={movetoMy} />
      </div>
      <div onClick={() => navigate("/study")}>어떤 공부를 하고계신가요?</div>
    </Wrapper>
  );
};

export default FeedPost;

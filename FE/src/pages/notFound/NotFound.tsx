import { useNavigate } from "react-router-dom";

import NotFoundLottie from "../../components/animation/NotFound";
import { Button } from "../../components/main/CommentPostStyle";

import * as S from "./NotFound.styled";

function NotFound() {
  const navigate = useNavigate();

  const handleClickHomeButton = () => {
    navigate("/");
    location.reload();
  };

  const handleClickReturnButton = () => {
    navigate(-1);
  };

  return (
    <S.Container>
      <NotFoundLottie />
      <S.Title>존재하지 않는 페이지/요청 입니다.</S.Title>
      <S.ButtonWrapper>
        <Button onClick={handleClickHomeButton}>홈으로</Button>
        <Button onClick={handleClickReturnButton}>뒤로가기</Button>
      </S.ButtonWrapper>
    </S.Container>
  );
}

export default NotFound;

import emotionStyled from "@emotion/styled";
import { colorG, colorWW } from "../../constants/colors";

const styled = emotionStyled;

const Wrapper = styled.div`
  z-index: 99;
  position: fixed;
  top: 0;
  flex: 0 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  width: 100%;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  padding: 0 20px 0 10px;
  & > div {
    flex: 1;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: end;
    margin-left: 30px;
    gap: 20px;
  }
`;

const Icon = styled.img`
  display: block;
  height: 20px;
  cursor: pointer;
`;

const ProfileIcon = styled.img`
  display: block;
  width: 28px;
  height: 28px;
  cursor: pointer;
  border-radius: 50%;
  overflow: hidden;
  object-fit: cover;
`

const QuickMenu = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  gap: 30px;
`;

const LogoWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 110px;
`

const Logo = styled.img`
  width: 90px;
  margin-left: 12px;
`

export { Icon, ProfileIcon, QuickMenu, Wrapper, LogoWrapper, Logo };

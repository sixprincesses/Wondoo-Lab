import { colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  z-index: 99;
  position: relative;
  cursor: pointer;
  &:hover {
    background: ${colorG};
  }
  & > div > div:nth-of-type(2) {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px;
  }
`;

const Profile = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  & > img {
    width: 35px;
    height: 35px;
    border-radius: 50%;
    object-fit: cover;
  }
`;

const Nickname = styled.div`
  flex: 1;
  font-size: 14px;
  font-weight: 600;
`;

const UserState = styled.div`
  display: flex;
  align-items: center;
  font-size: 12px;
  & > img {
    height: 16px;
  }
`;

export { Nickname, Profile, UserState, Wrapper };

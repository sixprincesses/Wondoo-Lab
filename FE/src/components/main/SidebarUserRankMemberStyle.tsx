import { color3, colorW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

const Profile = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  & > img {
    width: 40px;
    height: 40px;
    object-fit: cover;
    border-radius: 50%;
  }
  &:hover {
    opacity: 0.7;
  }
`;

const Nickname = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  & span:nth-of-type(1) {
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
  & p {
    font-size: 14px;
    font-weight: 200;
  }

  img {
    margin: auto 0;
  }
`;

const NicknameBox = styled.div`
  display: flex;
  align-items: center;

  img {
    margin-left: 4px;
  }
`

const Chat = styled.div`
  width: 70px;
  line-height: 30px;
  background: ${color3};
  color: ${colorW};
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-itmes: center;
  font-size: 14px;
  &:hover {
    opacity: 0.7;
  }
`;

export { Nickname, NicknameBox, Profile, Wrapper, Chat };

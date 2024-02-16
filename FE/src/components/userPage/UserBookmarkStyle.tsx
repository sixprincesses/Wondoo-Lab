import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  border: 1px solid ${colorG};
  border-radius: 10px;
  padding: 10px;
  background-color: ${colorWW};
`;

const Profile = styled.div`
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid ${colorG};
  border-radius: 50%;
  & > img {
    width: 40px;
    height: 40px;
    object-fit: cover;
  }
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: start;
  font-size: 14px;
  & > p {
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
  & > h3 {
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
`;

export { Content, Profile, Wrapper };

import { color1, colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  align-items: start;
  gap: 10px;
  padding: 5px;
  & > div:nth-of-type(2) {
    width: 454px;
    display: flex;
    flex-direction: column;
    gap: 5px;
  }
`;

const Profile = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 5px;
  cursor: pointer;
  & > img {
    width: 30px;
    height: 30px;
    object-fit: cover;
    border-radius: 50%;
    overflow: hidden;
  }
  &:hover {
    opacity: 0.7;
  }
`;

const ContentPut = styled.form`
  line-height: 24px;
  border-radius: 10px;
  background: ${colorG};
  padding: 3px 10px;
  & > input {
    width: 100%;
    height: 100%;
  }
  & > button {
    display: none;
  }
`;

const Content = styled.div`
  width: 100%;
  word-wrap: break-word;
  background: ${color1};
  border-radius: 10px;
  padding: 5px 10px;
`;

export { Content, ContentPut, Profile, Wrapper };

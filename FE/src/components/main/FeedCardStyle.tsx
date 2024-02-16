import emotionStyled from "@emotion/styled";
import { Link } from "react-router-dom";
import { color3, colorG, colorWW } from "../../constants/colors";
import { ViewerContainerProps } from "../../interfaces/ToastUIViewerProps";

const styled = emotionStyled;

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 50px 1fr;
  grid-template-rows: 1fr 40px;
  width: 630px;
  max-height: 420px;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 10px;
  padding: 10px;
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  & > img:nth-of-type(1) {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin: 0 0 10px 0;
    object-fit: cover;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
  & > div:nth-of-type(1) {
    flex: 1;
    width: 1px;
    border: 1px solid ${colorG};
    border-radius: 2px;
    margin-bottom: 10px;
  }
`;

const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin: 0 0 0 10px;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
`;

const Nickname = styled.span`
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  &:hover {
    text-decoration: underline;
  }
`;

const Title = styled.h2`
  cursor: pointer;
  &:hover {
    opacity: 0.5;
  }
`;

const ViewerContainer = styled.div<ViewerContainerProps>`
  position: relative;
  & > div {
    max-height: ${(props) => props.maxHeight}px;
    margin-right: 5px;
    padding-right: 10px;
    overflow-y: scroll;
    cursor: pointer;
  }
  & .front-shader {
    position: absolute;
    top: 0;
    display: none;
    width: 100%;
    height: 40px;
    background: linear-gradient(
      to bottom,
      rgba(255, 255, 255, 1),
      rgba(255, 255, 255, 0.8),
      rgba(255, 255, 255, 0)
    );
  }
  & .back-shader {
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 40px;
    background: linear-gradient(
      to top,
      rgba(255, 255, 255, 1),
      rgba(255, 255, 255, 0.8),
      rgba(255, 255, 255, 0)
    );
  }
`;

const Footer = styled.div`
  grid-column: 1/3;
  display: flex;
  align-items: end;
  justify-content: space-between;
`;

const Keywords = styled.div`
  width: 390px;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 0 20px;
  color: ${color3};
  font-size: 12px;
  margin-left: 25px;
`;

const Keyword = styled(Link)`
  color: ${color3};
  &:visited,
  &:active {
    color: ${color3};
  }
  cursor: pointer;
  &:hover {
    & > span {
      text-decoration: underline;
    }
  }
`;

const FeedBtns = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  & > * {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 35px;
    height: 35px;
    border-radius: 50%;
    cursor: pointer;
    &:hover {
      background: ${colorG};
    }
  }
  & img {
    height: 22px;
  }
`;

export {
  FeedBtns,
  Footer,
  Header,
  Keyword,
  Keywords,
  Main,
  Nickname,
  Profile,
  Title,
  ViewerContainer,
  Wrapper,
};

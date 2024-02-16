import { color3, colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 10px;
  padding: 10px;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  & > div:nth-of-type(1) {
    display: flex;
    align-items: center;
    justify-content: center;
    & > img {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      object-fit: cover;
      cursor: pointer;
      &:hover {
        opacity: 0.7;
      }
    }
  }
  & > div:nth-of-type(2) {
    flex: 1;
    display: flex;
    align-items: center;
    font-size: 18px;
    font-weight: 600;
    & > span {
      cursor: pointer;
      &:hover {
        text-decoration: underline;
      }
    }
  }
`;

const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-left: 15px;
  & > div:nth-of-type(1) {
    display: flex;
    align-items: end;
    gap: 10px;
    & > h2 {
      max-width: 490px;
    }
    & > p {
      flex: 1;
      font-size: 14px;
      font-weight: 200;
    }
    & > span {
      font-size: 14px;
    }
  }
  & > div:nth-of-type(2) {
    display: flex;
    gap: 10px;
    color: ${color3};
    font-size: 14px;
    & > a {
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
    }
  }
`;

const Keywords = styled.div`
  width: 95%;
  display: flex;
  flex-wrap: wrap;
`;

const More = styled.div`
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  display: flex;
  cursor: pointer;
  & > img {
    width: 20px;
    height: 20px;
    object-fit: cover;
    rotate: 90deg;
  }
  &:hover {
    background: ${colorG};
  }
`;

const Footer = styled.div`
  display: flex;
  justify-content: space-between;
  & > div:nth-of-type(1) {
    display: flex;
    align-items: center;
    font-size: 14px;
    font-weight: 600;
    margin-left: 10px;
  }
  & > div:nth-of-type(2) {
    display: flex;
    gap: 20px;
    & > * {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 40px;
      height: 40px;
      border-radius: 50%;
      cursor: pointer;
      &:hover {
        background: ${colorG};
      }
    }
    & img {
      height: 26px;
    }
  }
`;

export { Footer, Header, Keywords, Main, More, Wrapper };

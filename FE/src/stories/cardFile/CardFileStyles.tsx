import emotionStyled from "@emotion/styled";
import { color1, colorB, colorW } from "../../constants/colors";
import { container } from "./InterfaceContainer";
import { header } from "./InterfaceHeader";

const styled = emotionStyled;

const Container = styled.div<container>`
  border: 3px solid ${color1};
  border-radius: 10px;
  overflow: hidden;
  font-weight: 600;
  font-size: 12px;
  color: ${colorB};
  & .line-info {
    background: #ddf4ff;
    line-height: 30px;
    & p:nth-of-type(-n + 2) {
      background: #bbdfff;
    }
  }
  & .add {
    background: #e6ffec;
    & p:nth-of-type(-n + 2) {
      background: #ccffd8;
    }
  }
  & .sub {
    background: #ffebe9;
    & p:nth-of-type(-n + 2) {
      background: #ffd7d5;
    }
  }
`;

const Line = styled.div`
  display: grid;
  grid-template-columns: 50px 50px 22px 1fr;
  line-height: 20px;
  & p {
    margin: 0;
    text-align: right;
  }
  & p:nth-of-type(-n + 2) {
    padding: 0 10px;
  }
  & p:nth-of-type(3) {
    text-align: center;
    font-weight: 400;
  }
  & p:nth-of-type(4) {
    text-align: start;
  }
`;

const Header = styled.div<header>`
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: ${colorW};
  border-bottom: ${(props) => props.toggle && "3px solid" + color1};
  & div {
    height: 100%;
  }
  & button {
    height: 100%;
    aspect-ratio: 1/1;
    border: none;
    background-color: ${colorW};
    padding: 0;
    cursor: pointer;
  }
  & span {
    margin: 0 5px;
  }
  & p {
    margin: 0 10px;
  }
  & .file-info {
    display: flex;
    align-items: center;
    justify-content: start;
    & > *:nth-of-type(-n + 4) {
      border-right: 3px solid ${color1};
    }
    & .changes {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      aspect-ratio: 1/1;
    }
    & .file-name {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
    }
  }
  & .file-action {
    & > *:nth-of-type(-n + 2) {
      border-left: 3px solid ${color1};
    }
  }
`;

export { Container, Header, Line };

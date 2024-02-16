import { colorB, colorG, colorNG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Main = styled.div<{ isRolled: boolean }>`
  border-top: 1px solid ${colorG};
  & > div:nth-of-type(2) {
    z-index: 0;
    border-radius: 0 0 6px 6px;
    overflow: hidden;
  }
`;

const LangSelector = styled.div`
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: start;
  padding: 0 10px;
  border-bottom: 3px solid ${colorG};
`;
const Select = styled.div`
  z-index: 1;
  position: relative;
  border: 2px solid ${colorB};
  border-radius: 3px;
  background: ${colorWW};
`;
const Selected = styled.div`
  width: 120px;
  line-height: 26px;
  display: flex;
  align-items: center;
  justify-content: start;
  padding-left: 10px;
  color: ${colorB};
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
`;
const OptionBox = styled.div<{ isDisplay: boolean; isDown: boolean }>`
  z-index: 1;
  position: absolute;
  top: ${(props) => (props.isDown ? "29px" : "auto")};
  bottom: ${(props) => (props.isDown ? "auto" : "29px")};
  left: -3px;
  max-height: 100px;
  display: ${(props) => (props.isDisplay ? "flex" : "none")};
  flex-direction: column;
  border: 2px solid ${colorB};
  border-radius: 3px;
  background: ${colorWW};
  overflow: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  & > div {
    border-bottom: 1px solid ${colorB};
  }
  & > div:nth-last-of-type(1) {
    border-bottom: none;
  }
`;
const Option = styled.div`
  width: 120px;
  line-height: 26px;
  display: flex;
  align-items: center;
  justify-content: start;
  padding-left: 10px;
  color: ${colorB};
  cursor: pointer;
  &:hover {
    background: ${colorNG};
  }
`;
const Background = styled.div<{ isDisplay: boolean }>`
  z-index: 0;
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: ${(props) => (props.isDisplay ? "block" : "none")};
`;

export { Background, LangSelector, Main, Option, OptionBox, Select, Selected };

import { colorB, colorG, colorNG,  colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  z-index: 1;
  position: relative;
  border: 2px solid ${colorG};
  border-radius: 10px;
  background: ${colorWW};
`;
const Selected = styled.div`
  padding: 8px 20px;
  color: ${colorB};
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  &:hover {
    background: ${colorNG};
  }
`;
const OptionBox = styled.div<{ isDisplay: boolean; isDown: boolean }>`
  z-index: 1;
  position: absolute;
  top: ${(props) => (props.isDown ? "45px" : "auto")};
  bottom: ${(props) => (props.isDown ? "auto" : "45px")};
  left: -3px;
  max-height: 200px;
  display: ${(props) => (props.isDisplay ? "flex" : "none")};
  flex-direction: column;
  border: 3px solid ${colorWW};
  border-radius: 10px;
  background: ${colorG};
  overflow: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  & > div {
    border-bottom: 1px solid ${colorNG};
  }
  & > div:nth-last-of-type(1) {
    border-bottom: none;
  }
`;
const Option = styled.div`
  font-weight: 600;
  color: ${colorB};
  line-height: 40px;
  padding: 0 20px;
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

export { Background, Option, OptionBox, Selected, Wrapper };

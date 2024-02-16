import { color1, colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  min-width: 250px;
  display: flex;
  flex-direction: column;
  justify-content: start;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 10px 0 0 10px;
  & > div:nth-of-type(2) {
    flex: 1;
  }
`;

const Tab = styled.div`
  height: 40px;
  display: flex;
  & > button {
    flex: 1;
    background: transparent;
    border: none;
    border-bottom: 3px solid ${colorG};
    border-radius: 5px;
    cursor: pointer;
  }
  .active {
    background: ${color1};
  }
`;

export { Tab, Wrapper };

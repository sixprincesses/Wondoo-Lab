import { color1,  colorG, colorW, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 0 10px 10px 0;
  display: grid;
  grid-template-rows: calc(100% - 60px) 60px;
`;

const Selected = styled.div<{ live: boolean }>`
  position: relative;
  & > button {
    position: absolute;
    display: none; // 기능 구현시 제거
    top: 10px;
    right: 10px;
    width: 60px;
    line-height: 30px;
    background: ${color1};
    border: none;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
  & > div {
    height: ${(props) => (props.live ? 50 : 100)}%;
  }
`;

const Form = styled.form`
  display: flex;
  gap: 10px;
  padding: 10px;
  & > input {
    height: 100%;
    flex: 1;
    border: 2px solid ${colorG};
    border-radius: 20px 0 0 20px;
    outline: none;
    padding: 8px 15px;
  }
  & > button {
    height: 100%;
    border: none;
    border-radius: 0 20px 20px 0;
    background: ${color1};
    color: ${colorW};
    font-size: 16px;
    font-weight: 600;
    padding: 0 18px;
  }
`;

const Unselected = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export { Form, Selected, Unselected, Wrapper };

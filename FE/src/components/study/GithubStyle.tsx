import { color1, colorG, colorW, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

// 레이아웃
const Wrapper = styled.div`
  z-index: 0;
  display: grid;
  background-color: ${colorWW};
  grid-template-columns: 100%;
  grid-template-rows: 30px 1fr;
`;

// 헤더
const Header = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 10px;
`;

const Rollup = styled.div<{ isRolled: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  & > img {
    width: 16px;
    height: 16px;
    object-fit: cover;
    rotate: ${(props) => (props.isRolled ? "180deg" : "270deg")};
  }
`;

const Title = styled.div`
  flex: 1;
  font-weight: 600;
`;
const DragBtn = styled.div`
  position: absolute;
  top: 0px;
  left: calc(50% - 13px);
  cursor: grab;
  & > img {
    width: 26px;
    height: 26px;
    object-fit: cover;
  }
`;
const CloseBtn = styled.button`
  background: transparent;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  & > img {
    width: 20px;
    height: 20px;
    object-fit: cover;
  }
`;

// 메인
const Main = styled.div<{ isRolled: boolean }>`
  display: ${(props) => (props.isRolled ? "none" : "flex")};
  flex-direction: column;
  border-top: 1px solid ${colorG};
`;
const NicknameBox = styled.div`
  height: 50px;
  display: flex;
  align-items: center;
  padding: 10px 10px 0 10px;
`;
const SelectBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: start;
  flex-wrap: wrap;
  gap: 10px;
  padding: 10px 10px 0 10px;
`;
const Form = styled.form`
  position: relative;
  flex: 1;
  height: 100%;
  display: flex;
  align-items: center;
  & > div {
    flex: 1;
    height: 100%;
  }
  & > div > input {
    width: 100%;
    height: 100%;
    border: 2px solid ${colorG};
    border-radius: 10px 0 0 10px;
    outline: none;
    padding: 0 10px;
    &:disabled {
      background: #eeeeee;
    }
  }
  & > button {
    height: 100%;
    color: ${colorW};
    font-weight: 600;
    background: ${color1};
    border: none;
    border-radius: 0 10px 10px 0;
    padding: 0 10px 0 5px;
    cursor: pointer;
  }
`;
const FileBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: start;
  gap: 10px;
  padding: 5px;
`;

export {
  CloseBtn,
  DragBtn,
  FileBox,
  Form,
  Header,
  Main,
  NicknameBox,
  Rollup,
  SelectBox,
  Title,
  Wrapper,
};

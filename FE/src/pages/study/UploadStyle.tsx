import { color1, color2, color4, colorB, colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  width: 100%;
  min-width: 1110px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  & > div:nth-of-type(2) {
    height: 60%;
    border: 2px solid ${color1};
    border-radius: 5px;
  }
`;

const Preview = styled.div`
  height: 80%;
  padding: 0 15px;
  overflow-y: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  & > div {
    width: 630px;
    height: 100%;
    display: grid;
    grid-template-rows: 1fr 1fr 1fr;
    & > div:nth-of-type(1) {
      grid-row: 2/3;
    }
  }
`;

const Inputs = styled.div`
  padding: 15px;
  display: flex;
  flex-direction: column;
  background-color: ${colorWW};
  border: 2px solid ${colorG};
  border-radius: 5px;
  justify-content: start;
  gap: 40px;
  margin: 0 20px;
`;

const TitleBox = styled.div`
  width: 400px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  & > div:nth-of-type(1) {
    width: 100%;
    border: 2px solid ${color1};
    border-radius: 5px;
  }
  & > input {
    height: 50px;
    border: none;
    border-radius: 10px;
    outline: none;
    font-size: 22px;
    font-weight: 600;
    background: #e1e1e1;
    padding: 10px;
    &:focus {
      background: #b6b6b6;
    }
  }
`;

const KeywordBox = styled.div`
  flex: 1;
  width: 400px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  & > div:nth-of-type(1) {
    width: 100%;
    border: 2px solid ${color1};
    border-radius: 5px;
  }
`;

const Keywords = styled.form<{ isFocus: boolean }>`
  position: relative;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  border-radius: 10px;
  background: ${(props) => (props.isFocus ? "#b6b6b6" : "#e1e1e1")};
  padding: 10px;
  & > input {
    width: 380px;
    font-size: 14px;
    font-weight: 600;
    background: transparent;
    border: none;
    outline: none;
    padding: 10px;
  }
  & > label {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    cursor: text;
  }
`;

const Keyword = styled.div`
  max-width: 380px;
  display: flex;
  align-items: center;
  gap: 6px;
  border-radius: 50px;
  color: ${color4};
  font-size: 12px;
  font-weight: 600;
  background: ${color2};
  padding: 8px 10px 8px 20px;
  & > p {
    flex: 1;
    display: flex;
    align-items: start;
    gap: 6px;
    & > span {
      white-space: normal;
    }
  }
`;

const DeleteBtn = styled.button`
  width: 16px;
  height: 16px;
  line-height: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${color4};
  background: transparent;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  z-index: 3;
  &:hover {
    color: ${colorB};
    background: ${color4};
  }
`;

export {
  DeleteBtn,
  Inputs,
  Keyword,
  KeywordBox,
  Keywords,
  Preview,
  TitleBox,
  Wrapper,
};

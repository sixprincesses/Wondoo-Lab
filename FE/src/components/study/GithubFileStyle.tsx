import { colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  width: 100%;
  border: 1px solid ${colorG};
  border-radius: 10px;
  display: grid;
  grid-template-rows: 25px 1fr;
  overflow: hidden;
`;
const FileHeader = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 10px;
`;
const FileRollup = styled.div<{ isRolled: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  & > img {
    width: 10px;
    height: 10px;
    object-fit: cover;
    rotate: ${(props) => (props.isRolled ? "180deg" : "270deg")};
  }
`;
const FileTitle = styled.div`
  font-size: small;
`;
const FileContent = styled.div<{ isRolled: boolean }>`
  display: ${(props) => (props.isRolled ? "none" : "auto")};
  font-weight: 600;
  font-size: 12px;
  color: black;
  & .line-info {
    background: #bbdfff;
    line-height: 30px;
    & p:nth-last-of-type(-n + 2) {
      background: #ddf4ff;
    }
    & > div > label {
      /* display: none; */
    }
  }
  & .add {
    background: #ccffd8;
    & p:nth-last-of-type(-n + 2) {
      background: #e6ffec;
    }
  }
  & .sub {
    background: #ffd7d5;
    & p:nth-last-of-type(-n + 2) {
      background: #ffebe9;
    }
  }
`;
const Line = styled.div`
  display: grid;
  grid-template-columns: 20px 30px 30px 22px 1fr;
  line-height: 20px;
  & p {
    margin: 0;
  }
  & p:nth-of-type(-n + 2) {
    padding: 0 5px;
    text-align: right;
  }
  & p:nth-of-type(3) {
    text-align: center;
    font-weight: 400;
  }
  & p:nth-of-type(4) {
    text-align: start;
  }
`;
const CheckBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  & input {
    display: none;
    &:checked + label {
      background: gray;
    }
  }
  & label {
    display: inline-block;
    width: 12px;
    height: 12px;
    border: 2px solid gray;
    cursor: pointer;
  }
`;

export {
  CheckBox,
  FileContent,
  FileHeader,
  FileRollup,
  FileTitle,
  Line,
  Wrapper,
};

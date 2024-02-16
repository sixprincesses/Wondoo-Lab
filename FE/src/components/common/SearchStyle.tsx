import { colorNG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  height: 35px;
  width: 100%;
  max-width: 500px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: end;
`;

const Input = styled.input<{ isFocus: boolean }>`
  height: 100%;
  width: ${(props) => (props.isFocus ? "500px" : "50px")};
  border: none;
  border-radius: 30px;
  outline: none;
  padding: 10px 15px;
  background-color: ${colorNG};
  font-weight: 600;
  line-height: 0px;
  opacity: ${(props) => (props.isFocus ? 1 : 0)};
  transition: all 0.5s;
  display: flex;
  align-items: center;
`;

const Label = styled.label`
  position: absolute;
  right: 10px;
  top: calc(50% - 14px);
  display: flex;
  cursor: pointer;
  align-items: center;
`;

const Icon = styled.img`
  height: 20px;
  margin-top: 4px;
  object-fit: cover;
`;

const SearchResultBox = styled.div<{ isFocus: boolean }>`
  position: absolute;
  top: 50px;
  right: 0px;
  width: ${(props) => (props.isFocus ? "500px" : "50px")};
  height: ${(props) => (props.isFocus ? "auto" : "0px")};
  max-height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
  border-radius: 10px;
  background: ${colorNG};
  overflow: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  opacity: ${(props) => (props.isFocus ? 1 : 0)};
  transition: all 0.5s;
`;

const Data = styled.div`
  position: relative;
  width: 100%;
  height: auto;
`;

const NoData = styled.div`
  width: 100%;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export { Data, Icon, Input, Label, NoData, SearchResultBox, Wrapper };

import { colorNG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  border: 3px solid ${colorNG};
  border-radius: 10px;
  padding: 10px;
  font-weight: 600;
  & > div:nth-of-type(2) {
    text-align: center;
  }
  & > div:nth-of-type(3) {
    text-align: end;
  }
`;

export { Wrapper };

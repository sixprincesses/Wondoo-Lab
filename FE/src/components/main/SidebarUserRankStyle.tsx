import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 10px;
  padding: 10px;
`;

const Members = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export { Members, Wrapper };

import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-top: none;
  border-radius: 0 0 10px 10px;
  padding: 20px;
`;

const NoComment = styled.div``;

export { NoComment, Wrapper };

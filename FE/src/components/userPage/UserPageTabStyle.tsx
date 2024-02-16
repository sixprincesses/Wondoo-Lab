import { color3, colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivTab = styled.div`
  flex: 1;
  border-bottom: 1px solid ${color3};
  padding: 10px;
  text-align: center;
  cursor: pointer;
  margin-bottom: 10px;
  transition: background-color 0.3s ease;
  &:hover {
    background-color: ${colorG};
  }
`;

const DivTabListContainer = styled.div`
  width: 1020px;
  margin: 0 auto;
  display: flex;

  .selected {
    border-bottom: 3px solid ${color3};
  }
`;

export { DivTab, DivTabListContainer };

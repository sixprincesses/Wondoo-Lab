import { Outlet } from "react-router-dom";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
`;

const LayoutStudy = () => {
  return (
    <Wrapper>
      <Outlet />
    </Wrapper>
  );
};

export default LayoutStudy;

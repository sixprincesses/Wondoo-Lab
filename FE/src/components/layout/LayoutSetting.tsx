import { Outlet } from "react-router-dom";
import { styled } from "../../constants/styled";
import { ReactNodeProps } from "../../interfaces/ReactNodeProps";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 250px 1fr;
  padding-bottom: 320px;
`;

const LayoutMyPage = ({ children }: ReactNodeProps) => {
  return (
    <Wrapper>
      {children}
      <Outlet />
    </Wrapper>
  );
};

export default LayoutMyPage;

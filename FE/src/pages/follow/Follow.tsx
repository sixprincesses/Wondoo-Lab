import { Outlet } from "react-router-dom";
import { styled } from "../../constants/styled";
import { ReactNodeProps } from "../../interfaces/ReactNodeProps";
import { colorG } from "../../constants/colors";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr 800px 1fr;
  grid-template-rows: 50px calc(100% - 50px);
  border-right: 1px solid ${colorG};
  border-left: 1ps solid ${colorG};
`;

const Follow = ({ children }: ReactNodeProps) => {
  return (
    <Wrapper>
      {children}
      <Outlet />
    </Wrapper>
  );
};

export default Follow;

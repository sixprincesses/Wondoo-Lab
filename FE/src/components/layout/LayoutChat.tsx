import { Outlet } from "react-router";
import { styled } from "../../constants/styled";
import { ReactNodeProps } from "../../interfaces/ReactNodeProps";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 2fr 7fr;
  grid-template-rows: calc(100vh - 100px);
  gap: 20px;
  margin: 20px 30px;
`;

const LayoutChat = ({ children }: ReactNodeProps) => {
  return (
    <Wrapper>
      {children}
      <Outlet />
    </Wrapper>
  );
};

export default LayoutChat;

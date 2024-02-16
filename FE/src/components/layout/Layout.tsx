import { useEffect } from "react";
import { Outlet } from "react-router-dom";
import useConnect from "../../apis/websocket/useConnect";
import { styled } from "../../constants/styled";
import { ReactNodeProps } from "../../interfaces/ReactNodeProps";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";

const Wrapper = styled.div`
  position: relative;
  height: 100vh;
  display: grid;
  grid-template-rows: 50px calc(100vh - 50px);
  & > :nth-of-type(2) {
    grid-row: 2;
  }
`;

const Layout = ({ children }: ReactNodeProps) => {
  const connect = useConnect();
  const accessToken = localStorage.getItem("accessToken");
  const user_id = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  useEffect(() => {
    if (!accessToken) return;
    connect(user_id);
  }, [accessToken, user_id]);

  return (
    <Wrapper>
      {children}
      <Outlet />
    </Wrapper>
  );
};

export default Layout;

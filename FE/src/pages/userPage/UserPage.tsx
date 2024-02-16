import { useEffect } from "react";
import { Outlet } from "react-router";
import { useParams } from "react-router-dom";
import useGetMember from "../../apis/member/useGetMember";
import UserPageTabs from "../../components/userPage/UserPageTab";
import UserProfile from "../../components/userPage/UserProfile";
import { styled } from "../../constants/styled";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
  padding-bottom: 330px;
`;

const UserPage = () => {
  const { memberId } = useParams();
  const getMember = useGetMember();

  // 초기 로딩 로직
  useEffect(() => {
    if (typeof memberId !== "string") return;
    getMember(parseInt(memberId));
  }, [memberId]);

  return (
    <Container>
      <UserProfile />
      <UserPageTabs />
      <Outlet />
    </Container>
  );
};

export default UserPage;

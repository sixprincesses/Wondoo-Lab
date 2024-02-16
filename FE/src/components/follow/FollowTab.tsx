import { useEffect, useState } from "react";
import { Link, useLocation, useParams } from "react-router-dom";
import { colorB } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  grid-column: 2/3;
  display: flex;
  .active {
    box-shadow: 0px 2px ${colorB};
  }
`;

const Tab = styled(Link)`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid ${colorB};
`;

const FollowTab = () => {
  const { memberId } = useParams();
  const loaction = useLocation();
  const parts = loaction.pathname.split("/");
  const nowPage = parts[parts.length - 1];
  const [nowTab, setNowTab] = useState("");

  // 현재탭 설정 로직
  useEffect(() => {
    setNowTab(nowPage);
  }, [nowPage]);

  return (
    <Wrapper>
      <Tab
        to={`/follow/${memberId}/followers`}
        className={nowTab === "followers" ? "active" : ""}
      >
        followers
      </Tab>
      <Tab
        to={`/follow/${memberId}/followings`}
        className={nowTab === "followings" ? "active" : ""}
      >
        followings
      </Tab>
    </Wrapper>
  );
};

export default FollowTab;

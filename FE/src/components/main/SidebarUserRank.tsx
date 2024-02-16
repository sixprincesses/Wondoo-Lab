import styled from "@emotion/styled";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import SidebarUserRankMember from "./SidebarUserRankMember";
import { Members, Wrapper } from "./SidebarUserRankStyle";

const RankTitle = styled.div`
    font-weight: 600;
`

const SidebarUserRank = () => {
  const members = useAppSelector((state: RootState) => state.member.rank);

  return (
    <Wrapper>
      <RankTitle>
        Weekly Ranking
      </RankTitle>
      <Members>
        {members &&
          members.map((member, idx) => (
            <SidebarUserRankMember key={idx} member={member} />
          ))}
      </Members>
    </Wrapper>
  );
};

export default SidebarUserRank;

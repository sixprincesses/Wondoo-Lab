import { useNavigate } from "react-router-dom";
import styled from "@emotion/styled";
import rankDown from "../../assets/icon/rankDown.png";
import rankNoChange from "../../assets/icon/rankNoChange.png";
import rankUp from "../../assets/icon/rankUp.png";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { Keyword, Keywords, Wrapper } from "./SidebarKeywordStyle";

const KeywordTitle = styled.div`
    font-weight: 600;
`

const SidebarKeyword = () => {
  const navigate = useNavigate();
  const keywords = useAppSelector(
    (state: RootState) => state.feed.keywordRanks
  );

  const selectIcon = (change: number) => {
    if (change > 0) {
      return <img src={rankUp} alt="인기도 상승" />;
    } else if (change == 0) {
      return <img src={rankNoChange} alt="변화 없음" />;
    } else {
      return <img src={rankDown} alt="인기도 하락" />;
    }
  };

  return (
    <Wrapper>
      <KeywordTitle>
        Top Keywords
      </KeywordTitle>
      <Keywords>
        {keywords.map((keyword, idx) => (
          <Keyword key={idx}>
            <div>{keyword.rank}</div>
            <div onClick={() => navigate(`/search/${keyword.name}`)}>
              {keyword.name}
            </div>
            <div>
              {selectIcon(keyword.gap)}
              <span>{Math.abs(keyword.gap)}</span>
            </div>
          </Keyword>
        ))}
      </Keywords>
    </Wrapper>
  );
};

export default SidebarKeyword;

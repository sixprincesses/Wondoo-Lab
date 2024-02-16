import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useGetKeywordFeeds from "../../apis/feed/useGetKeywordFedds";
import FeedCard from "../../components/main/FeedCard";
import { colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";
import { initializeFeeds } from "../../store/feedSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";

const Layout = styled.div`
  display: grid;
  grid-template-columns: 1fr 630px 1fr;
  gap: 20px;
  overflow-y: scroll;
  & > :nth-of-type(1) {
    grid-column: 2;
  }
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  margin: 10px 0;
`;

const Header = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px double ${colorG};
  padding: 0 20px 0 0;
`;

const SearchFeeds = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const getKeywordFeeds = useGetKeywordFeeds();
  const { keyword } = useParams();
  const feeds = useAppSelector((state: RootState) => state.feed.feeds.feeds);

  // 최초 진입시 로딩
  useEffect(() => {
    dispatch(initializeFeeds());
    if (typeof keyword !== "string") {
      // console.log("키워드 파라미터 에러: 키워드가 문자열이 아닙니다.");
      navigate("/");
      return;
    }
    const params = {
      feed_id: null,
      keyword,
    };
    getKeywordFeeds(params);
  }, []);

  return (
    <Layout>
      <Wrapper>
        <Header>
          {/* <h2>검색 결과</h2>
          <span>Keyword: {keyword}</span> */}
        </Header>
        {feeds?.length ? (
          feeds.map((feed, idx) => <FeedCard key={idx} feed={feed} />)
        ) : (
          <div>피드가 없습니다.</div>
        )}
      </Wrapper>
    </Layout>
  );
};

export default SearchFeeds;

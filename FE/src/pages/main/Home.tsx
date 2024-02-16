import emotionStyled from "@emotion/styled";
import { useEffect } from "react";
import useGetFeeds from "../../apis/feed/useGetFeeds";
import FeedCard from "../../components/main/FeedCard";
import FeedPost from "../../components/main/FeedPost";
// import { GetFeedsQuery } from "../../interfaces/feed/GetFeedsQuery";
import { Target } from "../../constants/Target";
import useIntersect from "../../function/useObserver";
import { initializeFeeds } from "../../store/feedSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";

const styled = emotionStyled;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin: 10px 0;
`;

const Home = () => {
  const getFeeds = useGetFeeds();
  const dispatch = useAppDispatch();
  const feeds = useAppSelector((state: RootState) => state.feed.feeds.feeds);

  // 인피니티 스크롤
  const isEnd = useAppSelector((state: RootState) => state.feed.feeds.isEnd);
  const isFetching = useAppSelector(
    (state: RootState) => state.feed.feeds.isFetching
  );
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    if (!isEnd && !isFetching) {
      getFeeds();
    }
  });

  // 최초 진입시 로딩
  useEffect(() => {
    dispatch(initializeFeeds());
    const params = {
      feed_id: null,
    };
    getFeeds(params);
  }, []);

  return (
    <Wrapper>
      <FeedPost />
      {feeds?.length ? (
        feeds.map((feed, idx) => <FeedCard key={idx} feed={feed} />)
      ) : (
        <div>피드가 없습니다.</div>
      )}
      <Target ref={ref} />
    </Wrapper>
  );
};

export default Home;

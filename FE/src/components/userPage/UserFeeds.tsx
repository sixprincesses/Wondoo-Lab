import { useEffect } from "react";
import { useParams } from "react-router-dom";
import useGetMemberFeeds from "../../apis/feed/useGetMemberFeeds";
import { Target } from "../../constants/Target";
import { styled } from "../../constants/styled";
import useIntersect from "../../function/useObserver";
import { feed } from "../../interfaces/feed/FeedState";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { initializeMemberFeeds } from "../../store/memberSlice";
import { RootState } from "../../store/store";
import FeedCard from "../main/FeedCard";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin: 10px auto;
`;

const FeedContainer = styled.div`
  margin: auto;
`;

const UserFeeds = () => {
  const { memberId } = useParams();
  const dispatch = useAppDispatch();
  const getMemberFeeds = useGetMemberFeeds();
  const feeds: feed[] = useAppSelector(
    (state: RootState) => state.member.feed.memberFeeds
  );

  // 인피니티 스크롤
  const isEnd = useAppSelector((state: RootState) => state.member.feed.isEnd);
  const isFetching = useAppSelector(
    (state: RootState) => state.member.feed.isFetching
  );
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    if (typeof memberId !== "string") return;
    if (!isEnd && !isFetching) {
      const params = {
        member_id: parseInt(memberId),
      };
      getMemberFeeds(params);
    }
  });

  useEffect(() => {
    if (typeof memberId !== "string") return;
    dispatch(initializeMemberFeeds());
    const params = {
      member_id: parseInt(memberId),
      feed_id: null,
    };
    getMemberFeeds(params);
  }, [memberId]);

  return (
    <Container>
      {feeds.length ? (
        feeds.map((feed, idx) => (
          <FeedContainer key={idx}>
            <FeedCard feed={feed} />
          </FeedContainer>
        ))
      ) : (
        <div>피드가 없습니다.</div>
      )}
      <Target ref={ref} />
    </Container>
  );
};

export default UserFeeds;

import { useEffect } from "react";
import { useParams } from "react-router-dom";
import useGetFollowers from "../../apis/member/useGetFollowers";
import { Target } from "../../constants/Target.tsx";
import useIntersect from "../../function/useObserver.tsx";
import { initializeFollowers } from "../../store/followSlice.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import FollowMember from "./FollowMember";
import * as Styled from "./FollowStyle.tsx";

const Followers = () => {
  const { memberId } = useParams();
  const dispatch = useAppDispatch();
  const getFollowers = useGetFollowers();
  const accessToken = localStorage.getItem("accessToken");
  const followers = useAppSelector(
    (state: RootState) => state.follow.followers.members
  );

  // 인피니티 스크롤
  const isEnd = useAppSelector(
    (state: RootState) => state.follow.followers.isEnd
  );
  const isFetching = useAppSelector(
    (state: RootState) => state.follow.followers.isFetching
  );
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    if (!isEnd && !isFetching) {
      getFollowers();
    }
  });

  useEffect(() => {
    if (!accessToken) return;
    if (typeof memberId !== "string") return;
    dispatch(initializeFollowers());
    getFollowers({ page: 0, member_id: parseInt(memberId) });
  }, [memberId]);

  return (
    <Styled.Wrapper>
      <Styled.FollowBox>
        {followers?.length ? (
          followers.map((follower) => (
            <FollowMember key={follower.member_id} member={follower} />
          ))
        ) : (
          <div>팔로워가 없습니다.</div>
        )}
      </Styled.FollowBox>
      <Target ref={ref} />
    </Styled.Wrapper>
  );
};

export default Followers;

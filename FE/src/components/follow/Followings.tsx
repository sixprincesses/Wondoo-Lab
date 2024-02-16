import { useEffect } from "react";
import { useParams } from "react-router-dom";
import useGetFollowings from "../../apis/member/useGetFollowings";
import { Target } from "../../constants/Target.tsx";
import useIntersect from "../../function/useObserver.tsx";
import { initializeFollowings } from "../../store/followSlice.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import FollowMember from "./FollowMember";
import * as Styled from "./FollowStyle.tsx";

const Followings = () => {
  const { memberId } = useParams();
  const dispatch = useAppDispatch();
  const getFollowings = useGetFollowings();
  const accessToken = localStorage.getItem("accessToken");
  const followings = useAppSelector(
    (state: RootState) => state.follow.followings.members
  );

  // 인피니티 스크롤
  const isEnd = useAppSelector(
    (state: RootState) => state.follow.followings.isEnd
  );
  const isFetching = useAppSelector(
    (state: RootState) => state.follow.followings.isFetching
  );
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    if (!isEnd && !isFetching) {
      getFollowings();
    }
  });

  useEffect(() => {
    if (!accessToken) return;
    if (typeof memberId !== "string") return;
    dispatch(initializeFollowings());
    getFollowings({ page: 0, member_id: parseInt(memberId) });
  }, [memberId]);

  return (
    <Styled.Wrapper>
      <Styled.FollowBox>
        {followings?.length ? (
          followings.map((following) => (
            <FollowMember key={following.member_id} member={following} />
          ))
        ) : (
          <div>팔로잉이 없습니다.</div>
        )}
      </Styled.FollowBox>
      <Target ref={ref} />
    </Styled.Wrapper>
  );
};

export default Followings;

import { useEffect } from "react";
import useGetFriends from "../../apis/member/useGetFriends";
import { Target } from "../../constants/Target";
import useIntersect from "../../function/useObserver";
import { initializeFriends } from "../../store/followSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import Friend from "./Friend";
import { NoFriend, Wrapper } from "./FriendListStyle";

const FriendList = () => {
  const dispatch = useAppDispatch();
  const getFriends = useGetFriends();
  const accessToken = localStorage.getItem("accessToken");
  const friends = useAppSelector(
    (state: RootState) => state.follow.friends.members
  );

  // 인피니티 스크롤
  const isEnd = useAppSelector(
    (state: RootState) => state.follow.friends.isEnd
  );
  const isFetching = useAppSelector(
    (state: RootState) => state.follow.friends.isFetching
  );
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    if (!isEnd && !isFetching) {
      getFriends();
    }
  });

  useEffect(() => {
    if (!accessToken) return;
    dispatch(initializeFriends());
    getFriends({ page: 0 });
  }, []);

  return (
    <Wrapper>
      {friends?.length ? (
        friends.map((member) => (
          <Friend key={member.member_id} member={member} />
        ))
      ) : (
        <NoFriend>친구가 없습니다.</NoFriend>
      )}
      <Target ref={ref} />
    </Wrapper>
  );
};

export default FriendList;

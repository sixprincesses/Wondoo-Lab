import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useGetBookmarks from "../../apis/feed/useGetBookmarks";
import { Target } from "../../constants/Target";
import useIntersect from "../../function/useObserver";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { initializeBookmarks } from "../../store/memberSlice";
import { RootState } from "../../store/store";
import UserBookmark from "./UserBookmark";
import { Wrapper } from "./UserBookmarksStyle";

const UserBookmarks = () => {
  const { memberId } = useParams();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const getBookmarks = useGetBookmarks();
  const bookmarks = useAppSelector(
    (state: RootState) => state.member.bookmark.bookmarks
  );
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  // 인피니티 스크롤
  const isEnd = useAppSelector(
    (state: RootState) => state.member.bookmark.isEnd
  );
  const isFetching = useAppSelector(
    (state: RootState) => state.member.bookmark.isFetching
  );
  const ref = useIntersect(async (entry, observer) => {
    observer.unobserve(entry.target);
    if (!isEnd && !isFetching) {
      getBookmarks();
    }
  });

  // 최초 요청
  useEffect(() => {
    if (typeof memberId !== "string") return;
    if (parseInt(memberId) !== userId) {
      navigate(-1);
      return;
    }
    dispatch(initializeBookmarks());
    getBookmarks({ last: null });
  }, [memberId]);

  return (
    <Wrapper>
      {bookmarks.length ? (
        bookmarks.map((bookmark) => (
          <UserBookmark key={bookmark.bookmark_id} bookmark={bookmark} />
        ))
      ) : (
        <div>북마크가 없습니다.</div>
      )}
      <Target ref={ref} />
    </Wrapper>
  );
};

export default UserBookmarks;

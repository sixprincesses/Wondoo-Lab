import { useEffect } from "react";
import { useParams } from "react-router";
import useGetComments from "../../apis/comment/useGetComments";
import useGetFeed from "../../apis/feed/useGetFeed";
import CommentPost from "../../components/main/CommentPost";
import Comments from "../../components/main/Comments";
import Feed from "../../components/main/Feed";
import { styled } from "../../constants/styled";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 10px;
`;

const Detail = () => {
  const { feedId } = useParams();
  const getFeed = useGetFeed();
  const getComments = useGetComments();
  const feed = useAppSelector((state: RootState) => state.feed.selectedFeed);

  const fetchFeed = () => {
    if (typeof feedId !== "string") {
      alert("잘못된 피드 아이디 입니다.");
      return;
    }
    getFeed(parseInt(feedId));
    getComments(parseInt(feedId));
  };

  useEffect(() => {
    fetchFeed();
  }, []);

  return (
    <Wrapper>
      <Feed feed={feed} />
      <div>
        <CommentPost />
        <Comments />
      </div>
    </Wrapper>
  );
};

export default Detail;

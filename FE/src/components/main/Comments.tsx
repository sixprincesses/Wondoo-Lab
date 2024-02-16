import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { NoComment, Wrapper } from "./CommentsStyle";
import Comment from "./Coomment";

const Comments = () => {
  const comments = useAppSelector(
    (state: RootState) => state.feed.selectedFeedComments
  );

  return (
    <Wrapper>
      {comments?.length ? (
        comments?.map((comment) => (
          <Comment key={comment.comment_id} comment={comment} />
        ))
      ) : (
        <NoComment>댓글이 없습니다.</NoComment>
      )}
    </Wrapper>
  );
};

export default Comments;

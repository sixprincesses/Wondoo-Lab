import { AxiosResponse, RawAxiosRequestConfig } from "axios";
import { comment } from "../../interfaces/feed/FeedState";
import { setComments } from "../../store/feedSlice";
import { useAppDispatch } from "../../store/hooks";
import axios from "../axios.tsx";

interface GetCommentResponse {
  comments: comment[];
}

const useGetComments = () => {
  const dispatch = useAppDispatch();

  const getCommentsParams = (feedId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/comment`,
      method: "get",
      params: {
        feed_id: feedId,
      },
    };

    return params;
  };

  const getComments = (feedId: number) => {
    axios
      .request(getCommentsParams(feedId))
      .then((res: AxiosResponse<GetCommentResponse>) => {
        // console.log("Get Comments Res");
        // console.log(res);
        dispatch(setComments(res.data.comments));
      })
      .catch(() => {
        // console.error(err);
      });
  };
  return getComments;
};

export default useGetComments;

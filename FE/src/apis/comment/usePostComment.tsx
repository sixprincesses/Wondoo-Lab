import { RawAxiosRequestConfig } from "axios";
import { PostCommentParams } from "../../interfaces/comment/PostCommentParams";
import axios from "../axios.tsx";
import useGetComments from "./useGetComments";

const usePostComment = () => {
  const accessToken = localStorage.getItem("accessToken");
  const getComments = useGetComments();

  const postCommentsParam = (params: PostCommentParams) => {
    // console.log(params);
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/comment`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        feed_id: params.feed_id,
        parent_id: params.parent_id ? params.parent_id : null,
        content: params.content,
      },
    };

    return axiosParams;
  };

  const postComment = (params: PostCommentParams) => {
    axios
      .request(postCommentsParam(params))
      .then(() => {
        // console.log("Post Comment Res");
        // console.log(res);
        getComments(params.feed_id);
      })
      .catch(() => {
        // console.error(err);
      });
  };

  return postComment;
};

export default usePostComment;

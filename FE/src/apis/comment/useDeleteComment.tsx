import { RawAxiosRequestConfig } from "axios";
import axios from "../axios";

interface DeleteCommentParams {
  feed_id: number;
  comment_id: number;
}

const useDeleteComment = () => {
  const accessToken = localStorage.getItem("accessToken");

  const deleteCommentParams = ({ comment_id }: DeleteCommentParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/comment`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        comment_id,
      },
    };

    return params;
  };

  const deleteComment = async (params: DeleteCommentParams) => {
    const result = await axios
      .request(deleteCommentParams(params))
      .then(() => {
        // console.log("Delete Comment Res: ");
        // console.log(res);
        return true;
      })
      .catch(() => {
        // console.error(err);
        return false;
      });

    return result;
  };

  return deleteComment;
};

export default useDeleteComment;

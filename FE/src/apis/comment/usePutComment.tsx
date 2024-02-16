import { AxiosError, RawAxiosRequestConfig } from "axios";
import axios from "../axios";

interface PutCommentParams {
  comment_id: number;
  content: string;
}

const usePutComment = () => {
  const accessToken = localStorage.getItem("accessToken");

  const putCommentParams = ({ comment_id, content }: PutCommentParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/comment`,
      method: "put",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        comment_id,
        content,
      },
    };

    return params;
  };

  const putComment = async (params: PutCommentParams) => {
    const result = axios
      .request(putCommentParams(params))
      .then(() => {
        // console.log("Put Comment Res");
        // console.log(res);
        return true;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return false;
      });

    return result;
  };

  return putComment;
};

export default usePutComment;

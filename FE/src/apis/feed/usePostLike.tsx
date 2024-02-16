import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface PostLikeParams {
  feed_id: number;
}

interface PostLikeResponse {
  is_liked: boolean;
}

const usePostLike = () => {
  const accessToken = localStorage.getItem("accessToken");

  const postLikeParams = ({ feed_id }: PostLikeParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/like`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        feed_id,
      },
    };

    return params;
  };

  const postLike = async (params: PostLikeParams) => {
    if (!accessToken) return;
    const result = await axios
      .request(postLikeParams(params))
      .then((res: AxiosResponse<PostLikeResponse>) => {
        console.log("Post Like Res");
        console.log(res);
        return res.data.is_liked;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return postLike;
};

export default usePostLike;

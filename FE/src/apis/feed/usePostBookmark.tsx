import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface PostBookmarkParams {
  feed_id: number;
}

interface PostBookmarkResponse {
  is_bookmarked: boolean;
}

const usePostBookmark = () => {
  const accessToken = localStorage.getItem("accessToken");

  const postBookmarkParams = ({ feed_id }: PostBookmarkParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/bookmark`,
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

  const postBookmark = async (params: PostBookmarkParams) => {
    if (!accessToken) return;
    const result = await axios
      .request(postBookmarkParams(params))
      .then((res: AxiosResponse<PostBookmarkResponse>) => {
        console.log("Post Bookmark Res");
        console.log(res);
        return res.data.is_bookmarked;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return postBookmark;
};

export default usePostBookmark;

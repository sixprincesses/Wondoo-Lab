import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface DeleteBookmarkParams {
  feed_id: number;
}

interface DeleteBookmarkResponse {
  is_bookmarked: boolean;
}

const useDeleteBookmark = () => {
  const accessToken = localStorage.getItem("accessToken");

  const deleteBookmarkParams = ({ feed_id }: DeleteBookmarkParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/bookmark`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        feed_id,
      },
    };
    return params;
  };

  const deleteBookmark = async (params: DeleteBookmarkParams) => {
    if (!accessToken) return;
    const result = await axios
      .request(deleteBookmarkParams(params))
      .then((res: AxiosResponse<DeleteBookmarkResponse>) => {
        console.log("Delete Bookmark Res");
        console.log(res);
        return res.data.is_bookmarked;
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });

    return result;
  };

  return deleteBookmark;
};

export default useDeleteBookmark;

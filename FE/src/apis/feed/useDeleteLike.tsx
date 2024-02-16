import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface DeleteLikeParams {
  feed_id: number;
}

interface DeleteLikeResponse {
  is_liked: boolean;
}

const useDeleteLike = () => {
  const accessToken = localStorage.getItem("accessToken");

  const deleteLikeParams = ({ feed_id }: DeleteLikeParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/like`,
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

  const deleteLike = async (params: DeleteLikeParams) => {
    if (!accessToken) return;
    const result = await axios
      .request(deleteLikeParams(params))
      .then((res: AxiosResponse<DeleteLikeResponse>) => {
        console.log("Delete Like Res");
        console.log(res);
        return res.data.is_liked;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return deleteLike;
};

export default useDeleteLike;

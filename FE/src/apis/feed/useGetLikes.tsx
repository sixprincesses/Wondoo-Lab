import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface GetLikesParams {
  last?: number;
  size: number;
  dir: "ASC" | "DESC";
}

interface like {
  like_id: number;
  feed_id: number;
  created_time: Date;
}

interface GetLikesResponse {
  likes: like[];
  last_like_id: number;
}

const useGetLikes = () => {
  const accessToken = localStorage.getItem("accessToken");

  const getLikesParams = ({ last, size, dir }: GetLikesParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/like`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        last,
        size,
        dir,
      },
    };

    return params;
  };

  const getLikes = (params: GetLikesParams) => {
    axios
      .request(getLikesParams(params))
      .then((res: AxiosResponse<GetLikesResponse>) => {
        console.log("Get Likes Res");
        console.log(res);
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return getLikes;
};

export default useGetLikes;

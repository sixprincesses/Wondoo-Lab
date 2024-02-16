import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios";

interface GetFeedMatchParams {
  feed_id: number;
}

const useGetFeedMatch = () => {
  const accessToken = localStorage.getItem("accessToken");

  const getFeedMatchParams = ({ feed_id }: GetFeedMatchParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/match/feed`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        feed_id: feed_id,
      },
    };

    return axiosParams;
  };

  const getFeedMatch = async (params: GetFeedMatchParams) => {
    const result = await axios
      .request(getFeedMatchParams(params))
      .then((res: AxiosResponse<{ similarity: number }>) => {
        console.log(res);
        return Math.round(res.data.similarity * 100) / 100;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return getFeedMatch;
};

export default useGetFeedMatch;

import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

const useGetLastFeed = () => {
  const accessToken = localStorage.getItem("accessToken");

  const getLastFeedParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/feed/last`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    };

    return params;
  };

  const getLastFeed = async () => {
    if (!accessToken) return;

    const result = await axios
      .request(getLastFeedParams())
      .then((res: AxiosResponse) => {
        console.log(res);
        return res.data;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return getLastFeed;
};

export default useGetLastFeed;

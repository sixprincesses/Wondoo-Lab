import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetTempFeedRespons } from "../../interfaces/tempFeed/GetTempFeedResponse";
import { useAppDispatch } from "../../store/hooks";
import { fetchData } from "../../store/tempFeedSlice";
import axios from "../axios.tsx";

const useGetTempFeed = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");

  const getTempFeedParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/tempfeed`,
      method: "get",
      params: {
        Authorization: `Bearer ${accessToken}`,
      },
    };

    return params;
  };

  const getTempFeed = () => {
    axios
      .request(getTempFeedParams())
      .then((res: AxiosResponse<GetTempFeedRespons>) => {
        console.log("Get Temp Feed Res");
        console.log(res);
        dispatch(fetchData(res.data));
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return getTempFeed;
};

export default useGetTempFeed;

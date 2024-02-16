import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import axios from "../axios";

const usePostTempFeed = () => {
  const accessToken = localStorage.getItem("accessToken");
  const content = useAppSelector(
    (state: RootState) => state.tempFeed.data.instanceData
  );
  const time_logs = useAppSelector(
    (state: RootState) => state.tempFeed.data.timelogs
  );

  const postTempFeedParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/tempfeed`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        content,
        time_logs,
      },
    };

    return params;
  };

  const postTempFeed = () => {
    axios
      .request(postTempFeedParams())
      .then((res: AxiosResponse) => {
        console.log("Post Temp Feed Res");
        console.log(res);
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return postTempFeed;
};

export default usePostTempFeed;

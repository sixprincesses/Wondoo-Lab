import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useAppDispatch } from "../../store/hooks";
import { initailizeData } from "../../store/tempFeedSlice";
import axios from "../axios";

const useDeleteTempFeed = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");

  const deleteTempFeedParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/tempfeed`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    };

    return params;
  };

  const deleteTempFeed = () => {
    axios
      .request(deleteTempFeedParams())
      .then((res: AxiosResponse) => {
        console.log("Delete Temp Feed Res");
        console.log(res);
        dispatch(initailizeData());
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return deleteTempFeed;
};

export default useDeleteTempFeed;

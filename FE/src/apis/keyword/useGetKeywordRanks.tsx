import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { setKeywordRanks } from "../../store/feedSlice";
import { useAppDispatch } from "../../store/hooks";
import axios from "../axios";

const useGetKeywordRanks = () => {
  const dispatch = useAppDispatch();

  const getKeywordRanksParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/keyword/rank`,
      method: "get",
    };

    return params;
  };

  const getKeywordRanks = () => {
    axios
      .request(getKeywordRanksParams())
      .then((res: AxiosResponse) => {
        console.log("Get Keyword Rank Res");
        console.log(res);
        dispatch(setKeywordRanks(res.data.ranks));
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return getKeywordRanks;
};

export default useGetKeywordRanks;

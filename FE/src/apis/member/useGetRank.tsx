import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { rank } from "../../interfaces/feed/FeedState.tsx";
import { useAppDispatch } from "../../store/hooks";
import { setRank } from "../../store/memberSlice";
import axios from "../axios.tsx";

const useGetRank = () => {
  const dispatch = useAppDispatch();

  const getRankParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/member/ranking`,
      method: "get",
    };

    return params;
  };

  const getRank = () => {
    axios
      .request(getRankParams())
      .then((res: AxiosResponse<{ member_ranking: rank[] }>) => {
        console.log("Get Rank Res");
        console.log(res);
        dispatch(setRank(res.data.member_ranking));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting rank: " + err);
      });
  };

  return getRank;
};

export default useGetRank;

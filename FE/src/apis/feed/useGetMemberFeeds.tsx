import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetFeedsResponse } from "../../interfaces/feed/GetFeedsResponse.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import {
  setMemberFeeds,
  setMemberFeedsFetching,
} from "../../store/memberSlice";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

interface GetMemberFeedsParams {
  member_id: number;
  feed_id?: null;
}

const useGetMemberFeeds = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");
  const feed_id = useAppSelector(
    (state: RootState) => state.member.feed.last_feed_id
  );

  const getMemberFeedsParams = (params: GetMemberFeedsParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/feeds/member`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        member_id: params.member_id,
        feed_id: params.feed_id === null ? params.feed_id : feed_id,
      },
    };

    return axiosParams;
  };

  const getMemberFeeds = async (params: GetMemberFeedsParams) => {
    dispatch(setMemberFeedsFetching(true));
    axios
      .request(getMemberFeedsParams(params))
      .then((res: AxiosResponse<GetFeedsResponse>) => {
        console.log("Get Member Feeds Res");
        console.log(res);
        dispatch(setMemberFeeds(res.data));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Member Feeds: " + err);
      })
      .finally(() => {
        dispatch(setMemberFeedsFetching(false));
      });
  };

  return getMemberFeeds;
};

export default useGetMemberFeeds;

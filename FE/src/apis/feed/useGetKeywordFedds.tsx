import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetFeedsResponse } from "../../interfaces/feed/GetFeedsResponse.tsx";
import { pushFeeds } from "../../store/feedSlice.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks.tsx";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

interface GetKeywordFeedsParams {
  feed_id?: null;
  keyword: string;
}

const useGetKeywordFeeds = () => {
  const dispatch = useAppDispatch();
  const feed_id = useAppSelector(
    (state: RootState) => state.feed.feeds.last_feed_id
  );
  const member_id = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  const getKeywordFeedsParams = (params: GetKeywordFeedsParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/feeds/keyword`,
      method: "get",
      params: {
        feed_id: params.feed_id === null ? params.feed_id : feed_id,
        keyword: params.keyword,
        member_id,
      },
    };

    return axiosParams;
  };

  const getKeywordFeeds = (params: GetKeywordFeedsParams) => {
    axios
      .request(getKeywordFeedsParams(params))
      .then((res: AxiosResponse<GetFeedsResponse>) => {
        console.log("Get Keyword Feeds Res");
        console.log(res);
        dispatch(pushFeeds(res.data));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Feeds: " + err);
      });
  };

  return getKeywordFeeds;
};

export default useGetKeywordFeeds;

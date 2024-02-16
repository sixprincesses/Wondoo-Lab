import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetFeedsResponse } from "../../interfaces/feed/GetFeedsResponse.tsx";
import { pushFeeds, setIsFetching } from "../../store/feedSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

interface GetFeedsParams {
  feed_id?: null;
}

const useGetFeeds = () => {
  const dispatch = useAppDispatch();
  const feed_id = useAppSelector(
    (state: RootState) => state.feed.feeds.last_feed_id
  );
  const member_id = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  const getFeedsParams = (params?: GetFeedsParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/feeds`,
      method: "get",
      params: {
        feed_id: params?.feed_id === null ? params.feed_id : feed_id,
        member_id,
      },
    };

    return axiosParams;
  };

  const getFeeds = (params?: GetFeedsParams) => {
    dispatch(setIsFetching(true));
    axios
      .request(getFeedsParams(params))
      .then((res: AxiosResponse<GetFeedsResponse>) => {
        console.log("Get Feeds Res");
        console.log(res);
        dispatch(pushFeeds(res.data));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Feeds: " + err);
        dispatch(pushFeeds({ feeds: [], last_feed_id: -1 }));
      })
      .finally(() => {
        dispatch(setIsFetching(false));
      });
  };

  return getFeeds;
};

export default useGetFeeds;

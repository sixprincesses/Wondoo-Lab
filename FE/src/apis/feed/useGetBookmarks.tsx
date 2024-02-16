import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetBookmarkResponse } from "../../interfaces/feed/GetookmarkResponse.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks.tsx";
import {
  setBookmarks,
  setBookmarksFetching,
} from "../../store/memberSlice.tsx";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

interface GetBookmarkParams {
  last?: null;
}

const useGetBookmarks = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");
  const last = useAppSelector(
    (state: RootState) => state.member.bookmark.last_bookmark_id
  );

  const getBookmarksParams = (params?: GetBookmarkParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/bookmark`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        last: params?.last === null ? params.last : last,
        size: 20,
      },
    };

    return axiosParams;
  };

  const getBookmarks = (params?: GetBookmarkParams) => {
    dispatch(setBookmarksFetching(true));
    axios
      .request(getBookmarksParams(params))
      .then((res: AxiosResponse<GetBookmarkResponse>) => {
        console.log("Get Bookmarks Res");
        console.log(res);
        dispatch(setBookmarks(res.data));
      })
      .catch((err: AxiosError) => {
        console.error(err);
      })
      .finally(() => {
        dispatch(setBookmarksFetching(false));
      });
  };

  return getBookmarks;
};

export default useGetBookmarks;

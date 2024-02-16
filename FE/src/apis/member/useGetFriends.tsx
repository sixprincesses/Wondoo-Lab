import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { getFollowsParams } from "../../interfaces/member/FollowParams.tsx";
import { getFollowersResponse } from "../../interfaces/member/FollowResponse";
import { setFriends, setFriendsFetching } from "../../store/followSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

const useGetFriends = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");
  const page = useAppSelector((state: RootState) => state.follow.friends.page);
  const size = useAppSelector((state: RootState) => state.follow.friends.size);

  const getFriendsParams = (params?: getFollowsParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/friends`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        page: params?.page ? params?.page : page,
        size: params?.size ? params?.size : size,
      },
    };

    return axiosParams;
  };

  const getFriends = async (params?: getFollowsParams) => {
    if (!accessToken) {
      console.log("비로그인 사용자");
      return;
    }
    dispatch(setFriendsFetching(true));
    axios
      .request(getFriendsParams(params))
      .then((res: AxiosResponse<getFollowersResponse>) => {
        console.log("Get Followings Res");
        console.log(res);
        const payload = {
          content: res.data.content,
          page: res.data.pageable.pageNumber + 1,
          isEnd: res.data.last,
        };
        dispatch(setFriends(payload));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Friends" + err);
      })
      .finally(() => {
        dispatch(setFriendsFetching(false));
      });
  };

  return getFriends;
};

export default useGetFriends;

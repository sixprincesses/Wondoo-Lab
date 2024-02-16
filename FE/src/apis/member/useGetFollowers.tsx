import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { getFollowsParams } from "../../interfaces/member/FollowParams.tsx";
import { getFollowersResponse } from "../../interfaces/member/FollowResponse";
import { setFollowers, setFollowersFetching } from "../../store/followSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

const useGetFollowers = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");
  const page = useAppSelector((state: RootState) => state.follow.friends.page);
  const size = useAppSelector((state: RootState) => state.follow.friends.size);

  const getFollowersParams = (params?: getFollowsParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/member/${
        params?.member_id
      }/followers`,
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

  const getFollowers = async (params?: getFollowsParams) => {
    if (!params?.member_id) {
      console.log("멤버 아이디 없음");
      return;
    }
    dispatch(setFollowersFetching(true));
    axios
      .request(getFollowersParams(params))
      .then((res: AxiosResponse<getFollowersResponse>) => {
        console.log("Get Followers Res");
        console.log(res);
        const payload = {
          content: res.data.content,
          page: res.data.pageable.pageNumber + 1,
          isEnd: res.data.last,
        };
        dispatch(setFollowers(payload));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Followers" + err);
      })
      .finally(() => {
        dispatch(setFollowersFetching(false));
      });
  };

  return getFollowers;
};

export default useGetFollowers;

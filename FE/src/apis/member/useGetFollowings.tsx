import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { getFollowsParams } from "../../interfaces/member/FollowParams.tsx";
import { getFollowersResponse } from "../../interfaces/member/FollowResponse";
import { setFollowings, setFollowingsFetching } from "../../store/followSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

const useGetFollowings = () => {
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");
  const page = useAppSelector((state: RootState) => state.follow.friends.page);
  const size = useAppSelector((state: RootState) => state.follow.friends.size);

  const getFollowingsParams = (params?: getFollowsParams) => {
    const axiosParams: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/member/${
        params?.member_id
      }/followings`,
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

  const getFollowings = async (params?: getFollowsParams) => {
    if (!params?.member_id) {
      console.log("멤버 아이디 없음");
      return;
    }
    dispatch(setFollowingsFetching(true));
    axios
      .request(getFollowingsParams(params))
      .then((res: AxiosResponse<getFollowersResponse>) => {
        console.log("Get Followings Res");
        console.log(res);
        const payload = {
          content: res.data.content,
          isEnd: res.data.last,
          page: res.data.pageable.pageNumber + 1,
        };
        dispatch(setFollowings(payload));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Followings" + err);
      })
      .finally(() => {
        dispatch(setFollowingsFetching(false));
      });
  };

  return getFollowings;
};

export default useGetFollowings;

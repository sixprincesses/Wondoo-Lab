import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useAppDispatch } from "../../store/hooks";
import { setIsFollow } from "../../store/memberSlice";
import axios from "../axios.tsx";

const useUnfollow = () => {
  const dispatch = useAppDispatch();
  const access_token = localStorage.getItem("accessToken");

  const unfollowParams = (toId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/${toId}/follow`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${access_token}`,
      },
    };
    return params;
  };

  const unfollow = async (toId: number) => {
    axios
      .request(unfollowParams(toId))
      .then((res: AxiosResponse) => {
        console.log("Unfollow Res");
        console.log(res);
        dispatch(setIsFollow(false));
      })
      .catch((err: AxiosError) => {
        console.error("Error unfollow: " + err);
      });
  };

  return unfollow;
};

export default useUnfollow;

import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useDispatch } from "react-redux";
import { setIsFollow } from "../../store/memberSlice";
import axios from "../axios.tsx";

const useFollow = () => {
  const dispatch = useDispatch();
  const access_token = localStorage.getItem("accessToken");

  const followParams = (toId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/${toId}/follow`,
      method: "post",
      headers: {
        Authorization: `Bearer ${access_token}`,
      },
    };
    return params;
  };

  const follow = async (toId: number) => {
    axios
      .request(followParams(toId))
      .then((res: AxiosResponse) => {
        console.log("Follow Res");
        console.log(res);
        dispatch(setIsFollow(true));
      })
      .catch((err: AxiosError) => {
        console.error("Error follow: " + err);
      });
  };

  return follow;
};

export default useFollow;

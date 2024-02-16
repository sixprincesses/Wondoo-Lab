import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { setRefreshToken } from "../../store/userSlice";
import axios from "../axios.tsx";

const useRefresh = () => {
  const dispatch = useAppDispatch();
  const member_id = useAppSelector(
    (state: RootState) => state.user.userInfo?.member_id
  );
  const refresh_token = useAppSelector(
    (state: RootState) => state.user.userInfo?.refresh_token
  );

  const refreshParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/refresh`,
      method: "post",
      headers: {
        member_id,
      },
      data: {
        refresh_token,
      },
    };
    return params;
  };

  const refresh = async () => {
    axios
      .request(refreshParams())
      .then(
        (
          res: AxiosResponse<{ access_token: string; refresh_token: string }>
        ) => {
          console.log("Refresh Res");
          console.log(res);
          dispatch(setRefreshToken(res.data));
        }
      )
      .catch((err: AxiosError) => {
        console.error("Error refreshing: " + err);
      });
  };

  return refresh;
};

export default useRefresh;

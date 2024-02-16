import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { setImageId } from "../../store/userSlice.tsx";
import axios from "../axios.tsx";

const useLogout = () => {
  const access_token = useAppSelector(
    (state: RootState) => state.user.userInfo?.access_token
  );
  const refresh_token = useAppSelector(
    (state: RootState) => state.user.userInfo?.refresh_token
  );
  const dispatch = useAppDispatch();

  const logoutParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/auth/member/logout`,
      method: "post",
      headers: {
        Authorization: "Bearer " + String(access_token),
      },
      data: {
        refresh_token: refresh_token,
      },
    };
    return params;
  };

  const logout = async () => {
    axios
      .request(logoutParams())
      .then((res: AxiosResponse) => {
        console.log("Logout Res");
        console.log(res);
        location.reload();
      })
      .catch((err: AxiosError) => {
        console.error("Errer logout: " + err);
      });
    dispatch(setImageId(""));
    localStorage.clear();
  };

  return logout;
};

export default useLogout;

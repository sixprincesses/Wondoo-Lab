import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useNavigate } from "react-router-dom";
import { LoginResponse } from "../../interfaces/signup/LoginResponse";
import { useAppDispatch } from "../../store/hooks";
import { login } from "../../store/userSlice";
import axios from "../axios.tsx";

const useLogin = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const loginParams = (authorizationCode: string) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/member/signin`,
      method: "post",
      data: {
        code: authorizationCode,
      },
      headers: {
        "Content-Type": "application/json",
      },
    };
    return params;
  };

  const userLogin = async (authorizationCode: string) => {
    axios
      .request(loginParams(authorizationCode))
      .then((res: AxiosResponse<LoginResponse>) => {
        console.log("Login Res");
        console.log(res);
        localStorage.setItem("accessToken", res.data.access_token);
        const token = {
          accessToken: res.data.access_token,
          refreshToken: res.data.refresh_token,
        };
        localStorage.setItem("token", JSON.stringify(token));
        dispatch(login(res.data));
        return res.status;
      })
      .then((status) => {
        if (status === 200) {
          navigate("/");
        } else if (status === 201) {
          navigate("/signup");
        }
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Access Token: " + err);
        navigate("/");
      });
  };

  return userLogin;
};

export default useLogin;

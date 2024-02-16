import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface PostTargetTimeBody {
  target_time: number;
}
const usePostTargetTime = () => {
  const accessToken = localStorage.getItem("accessToken");

  const postTargetTimeParams = (body: PostTargetTimeBody) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/targetTime`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: body,
    };

    return params;
  };

  const postTargetTime = async (body: PostTargetTimeBody) => {
    axios
      .request(postTargetTimeParams(body))
      .then((res: AxiosResponse) => {
        console.log("Post Target Time Res");
        console.log(res);
      })
      .catch((err: AxiosError) => {
        console.error("Error Posting Target Time: " + err);
      });
  };

  return postTargetTime;
};

export default usePostTargetTime;

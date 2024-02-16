import axios from "../axios.tsx";
import { RawAxiosRequestConfig } from "axios";

const useGetTargetTime = () => {
  const getTargetTimeParams = (memberId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/member/targetTime/${memberId}`,
      method: "get",
    };
    return params;
  };

  const getTargetTime = async (memberId: number) => {
    try {
      const res = await axios.request(getTargetTimeParams(memberId));
      console.log(res);
      return res.data.target_time;
    } catch (error) {
      console.error(error);
      return 4;
    }
  };

  return getTargetTime;
};

export default useGetTargetTime;

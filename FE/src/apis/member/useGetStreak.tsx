import axios from "../axios.tsx";
import { RawAxiosRequestConfig } from "axios";

const useGetStreak = () => {
  const getStreakParams = (memberId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/streak/${memberId}`,
      method: "get",
    };

    return params;
  };

  const getStreak = async (memberId: number) => {
    try {
      const res = await axios.request(getStreakParams(memberId));
      console.log(res);
      return res.data;
    } catch (error) {
      console.error(error);
    }
  };

  return getStreak;
};

export default useGetStreak;

import { RawAxiosRequestConfig } from "axios";
import axios from "../axios.tsx";

interface GetPlannerQuery {
  week: number;
}

const useGetPlanner = () => {
  const getPlannerParams = (memberId: number, query: GetPlannerQuery) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/member/planner/${memberId}`,
      method: "get",
      params: query,
    };
    return params;
  };

  const getPlanner = async (memberId: number, query: GetPlannerQuery) => {
    try {
      const res = await axios.request(getPlannerParams(memberId, query));
      console.log(res);
      return res.data.planners;
    } catch (error) {
      console.error(error);
      return [];
    }
  };

  return getPlanner;
};

export default useGetPlanner;

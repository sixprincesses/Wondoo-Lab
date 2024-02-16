import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { SubjectListByDay } from "../../interfaces/member/UserPlannerData";
import axios from "../axios.tsx";
import Swal from "sweetalert2";
import { color1 } from "../../constants/colors.ts";

interface PostPlannerBody {
  planners: SubjectListByDay[];
  today: string;
}

const usePostPlanner = () => {
  const accessToken = localStorage.getItem("accessToken");

  const postPlannerParams = (body: PostPlannerBody) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/planner`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: body,
    };

    return params;
  };

  const postPlanner = async (body: PostPlannerBody) => {
    axios
      .request(postPlannerParams(body))
      .then((res: AxiosResponse) => {
        console.log("Post Planner Res");
        Swal.fire({
          icon: "success",
          width: "600px",
          title: "계획 저장에 성공하였습니다.",
          confirmButtonColor: color1,
          confirmButtonText: "확인",
        });
        console.log(res);
      })
      .catch((err: AxiosError) => {
        console.error("Error Posting Planner: " + err);
      });
  };

  return postPlanner;
};

export default usePostPlanner;

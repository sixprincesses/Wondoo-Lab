import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetBranchesParams } from "../../interfaces/tempFeed/GetBranchesParams";
import axios from "../axios.tsx";

const useGetBranches = () => {
  const getBranchesParams = ({ nickname, repository }: GetBranchesParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/github/branch`,
      method: "get",
      params: {
        nickname,
        repository,
      },
    };
    return params;
  };

  const getBranches = async (params: GetBranchesParams) => {
    const result = await axios
      .request(getBranchesParams(params))
      .then((res: AxiosResponse<{ branches: string[] }>) => {
        console.log("Get Branches Res");
        console.log(res);
        return res.data.branches;
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });

    return result;
  };

  return getBranches;
};

export default useGetBranches;

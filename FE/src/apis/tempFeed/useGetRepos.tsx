import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetReposParams } from "../../interfaces/tempFeed/GetReposParams";
import axios from "../axios.tsx";

const useGetRepos = () => {
  const getReposParams = ({ nickname }: GetReposParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/github/repository`,
      method: "get",
      params: {
        nickname,
      },
    };
    return params;
  };

  const getRepos = async (params: GetReposParams) => {
    const result = await axios
      .request(getReposParams(params))
      .then((res: AxiosResponse<{ repositories: string[] }>) => {
        console.log("Get Repos Res");
        console.log(res);
        return res.data.repositories;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return getRepos;
};

export default useGetRepos;

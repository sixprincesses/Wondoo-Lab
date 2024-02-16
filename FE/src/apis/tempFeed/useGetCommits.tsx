import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { GetCommitsParams } from "../../interfaces/tempFeed/GetCommitsParams";
import axios from "../axios.tsx";

const useGetCommits = () => {
  const getCommitsParams = ({
    nickname,
    repository,
    branch,
  }: GetCommitsParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/github/commit`,
      method: "get",
      params: {
        nickname,
        repository,
        branch,
      },
    };
    return params;
  };

  const getCommits = async (params: GetCommitsParams) => {
    const result = await axios
      .request(getCommitsParams(params))
      .then(
        (
          res: AxiosResponse<{ commits: { message: string; sha: string }[] }>
        ) => {
          console.log("Get Commits Res");
          console.log(res);
          return res.data.commits;
        }
      )
      .catch((err: AxiosError) => {
        console.error(err);
      });

    return result;
  };

  return getCommits;
};

export default useGetCommits;

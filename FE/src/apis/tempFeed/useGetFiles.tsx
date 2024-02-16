import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { File } from "../../interfaces/tempFeed/File";
import { GetFilesParams } from "../../interfaces/tempFeed/GetFilesParams";
import axios from "../axios.tsx";

const useGetFiles = () => {
  const getFilesParams = ({ nickname, repository, commit }: GetFilesParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/article-service/github/changedfile`,
      method: "get",
      params: {
        nickname,
        repository,
        commit,
      },
    };
    return params;
  };

  const getFiles = (params: GetFilesParams) => {
    const result = axios
      .request(getFilesParams(params))
      .then((res: AxiosResponse<{ changedfiles: File[] }>) => {
        console.log("Get Files Res");
        console.log(res);
        return res.data.changedfiles;
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });

    return result;
  };

  return getFiles;
};

export default useGetFiles;

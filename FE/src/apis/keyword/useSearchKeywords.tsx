import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import axios from "../axios";

interface SearchKeywordsParams {
  keyword: string;
}

interface SearchKeywordsResponse {
  search_keywords: string[];
}

const useSearchKeywords = () => {
  const searchKeywordsParams = ({ keyword }: SearchKeywordsParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/keyword/search`,
      method: "get",
      params: {
        keyword,
      },
    };

    return params;
  };

  const searchKeywords = async (params: SearchKeywordsParams) => {
    const result = await axios
      .request(searchKeywordsParams(params))
      .then((res: AxiosResponse<SearchKeywordsResponse>) => {
        console.log("Search Keywords Res");
        console.log(res);
        return res.data.search_keywords;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return searchKeywords;
};

export default useSearchKeywords;

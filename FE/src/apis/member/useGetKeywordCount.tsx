import axios, { RawAxiosRequestConfig } from "axios";
import { KeywordCountQuery } from "../../interfaces/keyword/KeywordCountQuery";

const useGetKeywordCount = () => {
  const getKeywordCountParams = (query: KeywordCountQuery) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/keyword/member`,
      method: "get",
      params: query,
    };

    return params;
  };

  const getKeywordCount = async (query: KeywordCountQuery) => {
    try {
      const res = await axios.request(getKeywordCountParams(query));
      console.log(res);
      return res.data.keywords;
    } catch (error) {
      console.error(error);
    }
  };

  return getKeywordCount;
};

export default useGetKeywordCount;

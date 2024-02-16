import axios, { RawAxiosRequestConfig } from "axios";

const useGetStudyTime = () => {
  const getStudyTimeParams = (memberId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/article-service/feed/statistic/${memberId}`,
      method: "get",
    };

    return params;
  };

  const getStudyTime = async (memberId: number) => {
    try {
      const res = await axios.request(getStudyTimeParams(memberId));
      console.log(res);
      return res.data.feed_statistic;
    } catch (error) {
      console.error(error);
    }
  };

  return getStudyTime;
};

export default useGetStudyTime;

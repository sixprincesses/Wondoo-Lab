import axios, { RawAxiosRequestConfig } from "axios";

interface GetDiaryQuery {
  page: number;
  size: number;
  sort: string;
}

const useGetDiary = () => {
  const getDiaryParams = (memberId: number, query: GetDiaryQuery) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/member/diary/${memberId}`,
      method: "get",
      params: query,
    };

    return params;
  };

  const getDiary = async (memberId: number, query: GetDiaryQuery) => {
    try {
      const res = await axios.request(getDiaryParams(memberId, query));
      console.log(res);
      return res.data.content;
    } catch (error) {
      console.error(error);
    }
  };

  return getDiary;
};

export default useGetDiary;

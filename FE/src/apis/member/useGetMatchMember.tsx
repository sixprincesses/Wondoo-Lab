import axios, { RawAxiosRequestConfig } from "axios";

interface MatchMemberQuery {
  member_id1: number;
  member_id2: number;
}

const useGetMatchMember = () => {
  const accessToken = localStorage.getItem("accessToken");

  const getMatchMemberParams = (query: MatchMemberQuery) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/match/member`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: query,
    };

    return params;
  };

  const getMatchMember = async (query: MatchMemberQuery) => {
    try {
      const res = await axios.request(getMatchMemberParams(query));
      console.log(res);
      return res.data;
    } catch (error) {
      console.error(error);
    }
  };

  return getMatchMember;
};

export default useGetMatchMember;

import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { member } from "../../interfaces/feed/FeedState";
import axios from "../axios";

interface SearchMembersParams {
  keyword: string;
}

interface SearchMembersResponse {
  search_members: member[];
}

const useSearchMembers = () => {
  const searchMembersParams = ({ keyword }: SearchMembersParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/member`,
      method: "get",
      params: {
        keyword,
      },
    };

    return params;
  };

  const searchMembers = async (params: SearchMembersParams) => {
    const result = await axios
      .request(searchMembersParams(params))
      .then((res: AxiosResponse<SearchMembersResponse>) => {
        console.log("Search Member Res");
        console.log(res);
        return res.data.search_members;
      })
      .catch((err: AxiosError) => {
        console.error(err);
        return;
      });

    return result;
  };

  return searchMembers;
};

export default useSearchMembers;

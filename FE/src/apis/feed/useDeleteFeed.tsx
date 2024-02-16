import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useNavigate } from "react-router-dom";
import axios from "../axios.tsx";

interface DeleteFeedParams {
  feed_id: number;
}

const useDeleteFeed = () => {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem("accessToken");

  const deleteFeedParams = ({ feed_id }: DeleteFeedParams) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/auth/feed`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        feed_id,
      },
    };

    return params;
  };

  const deleteFeed = (params: DeleteFeedParams) => {
    axios
      .request(deleteFeedParams(params))
      .then((res: AxiosResponse) => {
        console.log("Delete Feed Res");
        console.log(res);
        navigate("/");
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return deleteFeed;
};

export default useDeleteFeed;

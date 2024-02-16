import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useNavigate } from "react-router-dom";
import { timeLog } from "../../interfaces/tempFeed/TempFeedState";
import { useAppDispatch } from "../../store/hooks";
import { initailizeData } from "../../store/tempFeedSlice";
import axios from "../axios.tsx";

interface PostFeedBody {
  feed_id: number;
  title: string;
  content: string;
  keywords: string[];
  time_logs: timeLog[];
}

const usePostFeed = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const accessToken = localStorage.getItem("accessToken");

  const postFeedParams = (body: PostFeedBody) => {
    const params: RawAxiosRequestConfig = {
      url: `https://dev.wondoo.kr/article-service/auth/feed`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: body,
    };

    return params;
  };

  const postFeed = async (body: PostFeedBody) => {
    axios
      .request(postFeedParams(body))
      .then((res: AxiosResponse) => {
        console.log("Post Feed Res: " + res);
        dispatch(initailizeData());
      })
      .catch((err: AxiosError) => {
        console.log("Error Posting Feed");
        console.error(err);
        alert("포스팅에 실패했습니다.");
      })
      .finally(() => {
        navigate("/");
      });
  };

  return postFeed;
};

export default usePostFeed;

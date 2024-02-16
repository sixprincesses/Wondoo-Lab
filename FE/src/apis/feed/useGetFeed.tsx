import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useNavigate } from "react-router-dom";
import { selectFeed } from "../../store/feedSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import axios from "../axios.tsx";

const useGetFeed = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const member_id = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  const getFeedParams = (feedId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/article-service/feed`,
      method: "get",
      params: {
        feed_id: feedId,
        member_id,
      },
    };
    return params;
  };

  const getFeed = async (feedId: number) => {
    axios
      .request(getFeedParams(feedId))
      .then((res: AxiosResponse) => {
        console.log("Get Feed Res");
        console.log(res);
        dispatch(selectFeed(res.data));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Feed: " + err);
        alert("잘못된 피드 번호입니다.");
        navigate("/");
      });
  };

  return getFeed;
};

export default useGetFeed;

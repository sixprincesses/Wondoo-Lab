import axios, { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";

const usePostDiary = () => {
  const accessToken = localStorage.getItem("accessToken");

  const postDiaryParams = () => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/auth/member/diary`,
      method: "post",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    };

    return params;
  };

  const postDiary = async () => {
    axios
      .request(postDiaryParams())
      .then((res: AxiosResponse) => {
        console.log("post diary success: ", res);
      })
      .catch((err: AxiosError) => {
        console.error("Error Posting Diary: " + err);
      });
  };

  return postDiary;
};

export default usePostDiary;

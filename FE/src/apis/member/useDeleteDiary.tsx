import axios, { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";

interface DeleteDiaryQuery {
  diary_id: string;
}

const useDeleteDiary = () => {
  const accessToken = localStorage.getItem("accessToken");

  const deleteDiaryParams = (query: DeleteDiaryQuery) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/auth/member/diary`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: query,
    };

    return params;
  };

  const deleteDiary = async (query: DeleteDiaryQuery) => {
    if (!accessToken) return;
    await axios
      .request(deleteDiaryParams(query))
      .then((res: AxiosResponse) => {
        console.log("Delete Diary Res " + res);
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return deleteDiary;
};

export default useDeleteDiary;

import axios from "../axios.tsx";
import { RawAxiosRequestConfig } from "axios";
import { useDispatch } from "react-redux";
import { setCover } from "../../store/memberSlice.tsx";

const useChangeBackground = () => {
  const dispatch = useDispatch();
  const access_token = localStorage.getItem("accessToken");

  const imgParams = (formData: FormData) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/auth/member/cover`,
      method: "post",
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${access_token}`,
      },
      data: formData,
    };
    return params;
  };

  const changeImg = (formData: FormData) => {
    axios
      .request(imgParams(formData))
      .then((response) => {
        console.log("이미지 응답", response.data);
        dispatch(setCover(response.data.image_id));
      })
      .catch((error) => {
        console.error("Error changing image: ", error, error.response);
        if (error.response && error.response.status === 413) {
          alert('이미지 용량이 너무 큽니다!');
        }
      });
  };

  return changeImg;
};

export default useChangeBackground;

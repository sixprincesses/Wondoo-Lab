import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../../store/hooks";
import { patchMember } from "../../store/userSlice";
import axios from "../axios.tsx";

interface JsonBody {
  nickname: string;
  name: string;
  email: string;
  phone: string;
  gender: string;
}

const usePutMember = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  // const user = useAppSelector((state) => state.user)
  const accessToken = localStorage.getItem("accessToken");

  const putMemberParams = (body: JsonBody) => {
    const params: RawAxiosRequestConfig = {
      url: `${import.meta.env.VITE_BASE_URL}/member-service/auth/member`,
      method: "put",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      data: body,
    };
    return params;
  };

  const putMember = async (body: JsonBody) => {
    axios
      .request(putMemberParams(body))
      .then((res: AxiosResponse) => {
        console.log("Put Memmber Res");
        console.log(res);
        dispatch(patchMember(res.data));
        return res.status;
      })
      .then((status) => {
        if (status === 200) {
          // const nickname = body.get("nickname");
          // const name = body.get("name");
          // const email = body.get("email");
          // const phone = body.get("phone");
          // const gender = body.get("gender")
          // if (email && phone && nickname && name && gender) {
          //   console.log("회원정보 수정중")
          //   dispatch(patchMember({ ...user, email: email.toString(), phone: phone.toString(), nickname: nickname.toString(), name: name.toString(), gender: gender.toString() }));
          // }
          navigate("/");
        } else {
          console.error("Error putting Member");
        }
      })
      .catch((err: AxiosError) => {
        console.error("Error putting Member: " + err);
      });
  };

  return putMember;
};

export default usePutMember;

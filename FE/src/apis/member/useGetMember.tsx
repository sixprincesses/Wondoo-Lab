import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { useNavigate } from "react-router-dom";
import { memberInfo } from "../../interfaces/member/MemberState";
import { useAppDispatch } from "../../store/hooks";
import { setMember } from "../../store/memberSlice";
import axios from "../axios.tsx";

const useGetMember = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const accessToken = localStorage.getItem("accessToken");

  const getMemberParams = (memberId: number) => {
    const params: RawAxiosRequestConfig = {
      url: `${
        import.meta.env.VITE_BASE_URL
      }/member-service/auth/member/${memberId}`,
      method: "get",
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    };
    return params;
  };

  const getMember = async (memberId: number) => {
    axios
      .request(getMemberParams(memberId))
      .then((res: AxiosResponse<memberInfo>) => {
        console.log("Get Member Res");
        console.log(res);
        dispatch(setMember(res.data));
      })
      .catch((err: AxiosError) => {
        console.error("Error getting Member: " + err);
        console.log("잘못된 유저 아이디");
        navigate("/");
      });
  };

  return getMember;
};

export default useGetMember;

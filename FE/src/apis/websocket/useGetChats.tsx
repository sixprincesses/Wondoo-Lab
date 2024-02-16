import { AxiosError, AxiosResponse, RawAxiosRequestConfig } from "axios";
import { message } from "../../interfaces/chat/MessageReceivePayload.tsx";
import { setPrevMessages } from "../../store/chatSlice";
import { useAppDispatch } from "../../store/hooks";
import axios from "../axios.tsx";

const useGetChats = () => {
  const dispatch = useAppDispatch();

  const gerChatParams = (reference: string) => {
    const params: RawAxiosRequestConfig = {
      method: "get",
      url: `${
        import.meta.env.VITE_MESSAGE_URL
      }/message-service/channel/${reference}`,
    };
    return params;
  };

  const getChats = async (reference: string) => {
    axios
      .request(gerChatParams(reference))
      .then((res: AxiosResponse<{ response: { messages: message[] } }>) => {
        console.log("Get Chats Res");
        console.log(res);
        dispatch(setPrevMessages(res.data.response.messages));
      })
      .catch((err: AxiosError) => {
        console.error(err);
      });
  };

  return getChats;
};

export default useGetChats;

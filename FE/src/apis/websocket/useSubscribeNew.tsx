import { useNavigate } from "react-router-dom";
import { setTab } from "../../store/chatSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import { stompClient } from "./client";
import useConnect from "./useConnect";

const useSubscribeNew = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const connect = useConnect();
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  const subscribeNew = (counterId: number) => {
    if (!stompClient) {
      console.log("클라이언트 없음");
      return;
    }
    if (stompClient.ws.readyState !== 1) {
      console.log("소켓 연결 안됨");
      location.reload();
      connect(userId);
      return;
    }
    const chatMessage = {
      sender_id: userId,
      receiver_id: counterId,
    };
    stompClient?.send(
      "/server/channel/create/simple",
      {},
      JSON.stringify(chatMessage)
    );
    dispatch(setTab("chat"));
    navigate("/chat");
  };

  return subscribeNew;
};

export { useSubscribeNew };

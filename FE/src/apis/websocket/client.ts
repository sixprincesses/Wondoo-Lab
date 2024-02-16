import SockJS from "sockjs-client/dist/sockjs";
import { Client, over } from "stompjs";

let stompClient: Client | null = null;
const Sock = new SockJS(`${import.meta.env.VITE_MESSAGE_URL}/message-service`);
stompClient = over(Sock);
stompClient.heartbeat = {
  incoming: 1000,
  outgoing: 1000,
};

export { stompClient };

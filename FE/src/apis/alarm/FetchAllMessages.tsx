import axios from "../axios.tsx";
import { ContentObject } from "../../interfaces/alarm/HeaderAlarm";

const FetchAllMessages = async (access_token: string | null) => {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_BASE_URL}/notification-service/auth/notification?page=0&size=5&sort=time,desc`,
      {
        headers: { 
          "Authorization": `Bearer ${access_token}`
        },
      }
    );
    const allContent: ContentObject[] = response.data.content.flatMap((obj:ContentObject) => obj);
    return allContent;
  } catch (error) {
    // console.log(error);
    return [];
  }
};

export default FetchAllMessages;

import axios from "../axios.tsx";
import { ContentObject } from "../../interfaces/alarm/HeaderAlarm";

const FetchMoreMessages = async (page: number, size: number, access_token: string | null) => {
  try {
    const response = await axios.get(
        `${import.meta.env.VITE_BASE_URL}/notification-service/auth/notification?page=${page}&size=${size}&sort=time,desc`,
        {
          headers: { 
            "Authorization": `Bearer ${access_token}`
          },        
        }
      );

    if (size >= response.data.totalElements) { return [] }

    const MoreContent = response.data.content.flatMap((obj:ContentObject) => obj);
    return MoreContent;
  } catch (error) {
    // console.log(error);
    return [];
  }
};

export default FetchMoreMessages;

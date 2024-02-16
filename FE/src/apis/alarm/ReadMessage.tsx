import axios from "../axios.tsx";

const ReadMessage = async (id: string, access_token: string | null) => {
  try {
    const res = await axios.post(`${import.meta.env.VITE_BASE_URL}/notification-service/auth/notification/${id}`, {}, {
        headers: { 
          "Authorization": `Bearer ${access_token}`
        },
      });
    
    const unread_count = res.data.unread_count
    return unread_count
  } catch (error) {
    // console.log(error);
    return
  }
};

export default ReadMessage;
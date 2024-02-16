import axios from "../axios.tsx";

const ReadAllMessages = async (access_token: string | null) => {
  try {
    await axios.post(`${import.meta.env.VITE_BASE_URL}/notification-service/auth/notification/read`, {}, {
        headers: { 
          "Authorization": `Bearer ${access_token}`
        },
      })
  } catch (error) {
    // console.log(error);
  }
  return
};

export default ReadAllMessages;
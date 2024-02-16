// 401 에러 발생 시 refresh 로직, 404 에러 발생 시 404 페이지로 보내는 로직을 axios intercept를 활용해 해결할 것
import axios from "axios";

const axiosInstance = axios.create({
  timeout: 5000,
});

axiosInstance.interceptors.request.use(function (config) {
  // console.log("intercept happened");
  const token = localStorage.getItem("token");

  if (!token) {
    config.headers["Authorization"] = null;
    config.headers["refreshToken"] = null;
    return config;
  }
  if (config.headers && token) {
    const { accessToken, refreshToken } = JSON.parse(token);
    // console.log(accessToken, refreshToken);
    config.headers["Authorization"] = `Bearer ${accessToken}`;
    config.headers["refreshToken"] = refreshToken;
    return config;
  }

  return config;
});

axiosInstance.interceptors.response.use(
  function (response) {
    // console.log("intercept", response);
    return response;
  },
  async function (err) {
    // await console.log(
    //   "intercept error",
    //   err,
    //   err.response,
    //   err.response.status
    // );
    const originalConfig = err.config;

    if (err.response && err.response.status === 401) {
      const accessToken = originalConfig.headers["Authorization"];
      const refreshToken = originalConfig.headers["refreshToken"];
      try {
        const res = await axios.post(
          `${import.meta.env.VITE_BASE_URL}/member-service/auth/member/refresh`,
          { refreshToken },
          {
            headers: {
              Authorization: accessToken,
            },
          }
        );

        if (res) {
          // console.log(res);
          const token = {
            accessToken: res.data.access_token,
            refreshToken: res.data.refresh_token,
          };
          localStorage.setItem("token", JSON.stringify(token));
          localStorage.setItem("accessToken", res.data.access_token);
          return axiosInstance.request(originalConfig);
        }
      } catch (err) {
        console.log("토큰 갱신 에러");
      }
      return Promise.reject(err);
    }
    return Promise.reject(err);
  }
);

export default axiosInstance;

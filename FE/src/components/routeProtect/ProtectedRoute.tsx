import { Navigate } from "react-router-dom";
import { ReactNodeProps } from "../../interfaces/ReactNodeProps";

const ProtectedRoute = ({ children }: ReactNodeProps) => {
  const accessToken = localStorage.getItem("accessToken");
  if (!accessToken) {
    alert("로그인이 필요한 서비스 입니다.");
    return <Navigate to="/" />;
  }
  return children;
};

export default ProtectedRoute;

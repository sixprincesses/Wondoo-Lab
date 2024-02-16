import { useEffect } from "react";
import useLogin from "../../apis/member/useLogin";

const GitHubCallback = () => {
  const login = useLogin();
  const url = new URL(window.location.href);
  const code = url.searchParams.get("code");

  useEffect(() => {
    if (code) {
      login(code);
    }
  }, [code]);

  return <></>;
};

export default GitHubCallback;

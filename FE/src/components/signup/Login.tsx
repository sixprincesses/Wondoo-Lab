import styled from "@emotion/styled";
import githubIcon from "../../assets/icon/github.png";

const LoginContainer = styled.div`
  width: 220px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  background-color: black;
  padding-left: 10px;
`;

const StyledButton = styled.button`
  color: white;
  background-color: black;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  cursor: pointer;
`;

const Icon = styled.img`
  width: 20px;
  height: 20px;
  filter: invert(1);
`;

const clientId = import.meta.env.VITE_CLIENT_ID

const Login = () => {
  const handleLogin = () => {
    window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientId}`;
  };

  return (
    <LoginContainer>
      <Icon src={githubIcon} alt="GitHub Icon" />
      <StyledButton onClick={handleLogin}>Login with GitHub</StyledButton>
    </LoginContainer>
  );
};

export default Login;

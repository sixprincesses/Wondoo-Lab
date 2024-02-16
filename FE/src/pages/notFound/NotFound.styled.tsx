import styled from "styled-components";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  flex-direction: column;
`;

const Title = styled.p`
  font-size: 20px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: space-around;

  width: 300px;
  margin-top: 50px;
`;

export { Container, Title, ButtonWrapper };

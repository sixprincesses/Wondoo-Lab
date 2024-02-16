import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";
import Timer from "./Timer";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 10px;
  padding: 10px;
  font-weight: 600;
  & > div:nth-of-type(2) {
    text-align: center;
  }
  & > div:nth-of-type(3) {
    text-align: end;
  }
`;

const TimerWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

const SidebarTimer = () => {
  return (
    <Wrapper>
      <div>Since Last Study</div>
      <TimerWrapper>
        <Timer />
      </TimerWrapper>
    </Wrapper>
  );
};

export default SidebarTimer;

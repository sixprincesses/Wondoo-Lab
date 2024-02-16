import styled from "@emotion/styled";

interface SignalProps {
    messageNum: number;
  }

const SignalWrapper = styled.div`
  position: absolute;
  top: 8px;
  right: 68px;
`;

const SignalCircle = styled.div`
  width: 20px;
  height: 20px;
  background-color: red;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: white;
`;

const Signal = ({ messageNum }: SignalProps) => {
  return messageNum > 0 ? (
    <SignalWrapper>
      <SignalCircle>{messageNum}</SignalCircle>
    </SignalWrapper>
  ) : null;
};

export default Signal;
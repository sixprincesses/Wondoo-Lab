import { colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const Channel = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  border-bottom: 1px solid ${colorG};
  margin: 5px;
  padding: 5px;
  cursor: pointer;
  &.active {
    border-bottom: 2px solid ${colorG};
  }
  & > div:nth-of-type(1) {
    display: flex;
    align-items: center;
    gap: 10px;
    & > img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      object-fit: cover;
    }
  }
  & > div:nth-of-type(2) {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
  }
`;

const NoChannel = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export { Channel, NoChannel, Wrapper };

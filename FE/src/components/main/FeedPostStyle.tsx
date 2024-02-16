import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 10px;
  border: 3px solid ${colorG};
  background: ${colorWW};
  & > div:nth-of-type(1) {
    display: flex;
    align-items: center;
    justify-content: center;
    & > img {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      overflow: hidden;
      object-fit: cover;
    }
  }
  & > div:nth-of-type(2) {
    flex: 1;
    display: flex;
    align-items: center;
    border-radius: 10px;
    height: 100%;
    font-weight: 600;
    background: ${colorG};
    padding: 10px 20px;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }

    .image {
      border-radius: 50%;
      overflow: hidden;
      object-fit: cover;
    }
  }
`;

export { Wrapper };

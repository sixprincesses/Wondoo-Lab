import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-radius: 10px;
  padding: 10px;
`;

const Keywords = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 10px;
`;

const Keyword = styled.div`
  display: grid;
  grid-template-columns: 20px 196px 40px;
  gap: 10px;
  & > div:nth-of-type(1) {
    text-align: end;
    font-weight: 600;
  }
  & > div:nth-of-type(2) {
    word-wrap: break-word;
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
  & > div:nth-of-type(3) {
    display: flex;
    align-items: center;
    gap: 10px;
    & > img {
      height: 10px;
    }
    & > span {
      font-size: small;
    }
  }
  & > a {
    display: flex;
    align-items: center;
    justify-content: center;
    & > img {
      width: 15px;
      height: 15px;
      object-fit: cover;
    }
  }
`;

export { Keyword, Keywords, Wrapper };

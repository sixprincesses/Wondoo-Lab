import { color1, color3, colorB, colorG } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 10px;
  & > h3 {
    color: ${color3};
    font-size: 16px;
  }
  & > div:nth-of-type(1) {
    width: 100%;
    border: 2px solid ${color3};
    border-radius: 2px;
  }
`;

const Members = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 20px;
  padding: 10px 0;
`;

const Member = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  border: 1px solid ${colorB};
  border-radius: 5px;
  padding: 5px;
  cursor: pointer;
  &:hover {
    background: ${colorG};
  }
  & > img {
    width: 40px;
    height: 40px;
    object-fit: cover;
  }
  & > p {
    color: ${colorB};
    font-size: 16px;
    font-weight: 600;
  }
`;

const Keywords = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: start;
  justify-content: start;
  gap: 20px;
  line-height: 24px;
  padding: 10px 0;
`;

const Keyword = styled.p`
  color: ${color1};
  font-size: 16px;
  cursor: pointer;
  &:hover {
    & > span {
      text-decoration: underline;
    }
  }
`;

const NoData = styled.div`
  grid-column: 1/4;
  width: 100%;
`;

export { Keyword, Keywords, Member, Members, NoData, Wrapper };

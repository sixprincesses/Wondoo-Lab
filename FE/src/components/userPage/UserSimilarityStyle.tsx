import { colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivUserSimilarityContainer = styled.div`
  width: 1020px;
  min-height: 1020px;
  margin: 0 auto;
`;

const DivUserSimilarityHeaderContainer = styled.div`
  border-radius: 5px;
  background-color: ${colorWW};

  width: 90%;

  margin: 30px auto;
  padding: 30px;

  display: flex;

  h1 {
    margin: 20px;
  }
`;

const DivSimilarityHeaderContent = styled.div`
  border-right: 2px solid black;
  &.header-right {
    border: none;
    margin: auto 0 auto auto;
  }
`;

const DivSimilarityPercent = styled.div`
  margin: 0 40px 0 70px;
  .int {
    font-size: 100px;
    font-weight: bolder;
  }

  .decimal {
    font-size: 50px;
  }

  .percent {
    font-weight: bolder;
  }
`;

const DivStudyTimeContainer = styled.div`
  border-bottom: 2px solid black;
  display: flex;
`;

const DivStudyTime = styled.div`
  margin: 0 10px;
  text-align: center;

  span {
    font-size: 16px;
  }
`;

const DivStudyTimeDiff = styled.div`
  margin: 10px auto;
  text-align: center;

  span {
    font-size: 16px;
  }
`;

const DivChartContainer = styled.div`
  border-radius: 5px;
  background-color: ${colorWW};

  width: 90%;
  margin: 30px auto;
  padding: 30px;
`;
export {
  DivUserSimilarityContainer,
  DivSimilarityHeaderContent,
  DivSimilarityPercent,
  DivUserSimilarityHeaderContainer,
  DivStudyTimeContainer,
  DivStudyTime,
  DivStudyTimeDiff,
  DivChartContainer,
};

import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivUserStatsContainer = styled.div`
  width: 1020px;
  margin: auto;
`;

const DivChartListContainer = styled.div`
  display: flex;
  margin: 30px auto;
  gap: 40px;
`;

const DivChartContainer = styled.div`
  width: 600px;
  height: 520px;
  background-color: ${colorWW};
  border: 1px solid ${colorG};
  border-radius: 5px;
  padding: 30px;
`;

const ChartContainer = styled.div`
  width: 100%;
  border-radius: 5px;
  background-color: ${colorWW};
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
`;

export {
  ChartContainer,
  DivChartContainer,
  DivChartListContainer,
  DivUserStatsContainer,
};

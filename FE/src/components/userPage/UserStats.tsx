import ChartBar from "./ChartBar";
import ChartLine from "./ChartLine";
import ChartStreak from "./ChartStreak";
import {
  DivChartContainer,
  DivChartListContainer,
  DivUserStatsContainer,
  ChartContainer,
} from "./UserStatsStyle";

const UserStats = () => {
  return (
    <DivUserStatsContainer>
      <ChartContainer>
        <ChartStreak />
      </ChartContainer>
      <DivChartListContainer>
        <DivChartContainer>
          <h2>키워드 별 태그 횟수</h2>
          <ChartBar />
        </DivChartContainer>
        <DivChartContainer className="chart-bar">
          <h2>월 별 공부 시간</h2>
          <ChartLine />
        </DivChartContainer>
      </DivChartListContainer>
    </DivUserStatsContainer>
  );
};

export default UserStats;

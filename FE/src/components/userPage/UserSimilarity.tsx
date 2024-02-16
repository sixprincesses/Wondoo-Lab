import { useEffect, useState } from "react";
import ChartBarVertical from "./ChartBarVertical";
import {
  DivChartContainer,
  DivSimilarityHeaderContent,
  DivSimilarityPercent,
  DivStudyTime,
  DivStudyTimeContainer,
  DivStudyTimeDiff,
  DivUserSimilarityContainer,
  DivUserSimilarityHeaderContainer,
} from "./UserSimilarityStyle";
import useGetMatchMember from "../../apis/member/useGetMatchMember";
import { useAppSelector } from "../../store/hooks";
import { useNavigate, useParams } from "react-router-dom";
import { RootState } from "../../store/store";

interface StudyTime {
  day: number;
  hour: number;
  minute: number;
  second: number;
}

interface MatchingData {
  similarity: number;
  labels: string[];
  data1: number[];
  data2: number[];
  study_time1: StudyTime;
  study_time2: StudyTime;
  study_time_gap: StudyTime;
  more_than: boolean;
}

const UserSimilarity = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [similarity, setSimilarity] = useState(0);
  const [labels, setLabels] = useState<string[]>([]);
  const [dataList1, setDataList1] = useState<number[]>([]);
  const [dataList2, setDataList2] = useState<number[]>([]);
  const [studyTime1, setStudyTime1] = useState<StudyTime>({
    day: 0,
    hour: 0,
    minute: 0,
    second: 0,
  });
  const [studyTime2, setStudyTime2] = useState<StudyTime>({
    day: 0,
    hour: 0,
    minute: 0,
    second: 0,
  });
  const [studyTimeGap, setStudyTimeGap] = useState<StudyTime>({
    day: 0,
    hour: 0,
    minute: 0,
    second: 0,
  });
  const [moreThan, setMoreThan] = useState(false);
  const { memberId } = useParams();
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );
  const getMatchMember = useGetMatchMember();
  const navigate = useNavigate();

  useEffect(() => {
    if (memberId === undefined) return;

    if (+memberId === userId) {
      navigate(-1);
      return;
    }
    const fetchData = async () => {
      setIsLoading(true);
      const newMatchingData: MatchingData = await getMatchMember({
        member_id1: userId,
        member_id2: +memberId,
      }).finally(() => setIsLoading(false));

      setSimilarity(newMatchingData.similarity);

      setLabels(newMatchingData.labels);

      setDataList1(newMatchingData.data1);
      setDataList2(newMatchingData.data2);

      setStudyTime1(newMatchingData.study_time1);
      setStudyTime2(newMatchingData.study_time2);
      setStudyTimeGap(newMatchingData.study_time_gap);
      setMoreThan(newMatchingData.more_than);
    };

    fetchData();
  }, []);

  if (isLoading) {
    return <></>;
  }
  return (
    <DivUserSimilarityContainer>
      <DivUserSimilarityHeaderContainer>
        <DivSimilarityHeaderContent>
          <h1>적합도</h1>
          <DivSimilarityPercent>
            <span className="int">{Math.floor(similarity)}.</span>
            <span className="decimal">
              {Math.round((similarity - Math.floor(similarity)) * 100)}
              <span className="percent">%</span>
            </span>
          </DivSimilarityPercent>
        </DivSimilarityHeaderContent>
        <DivSimilarityHeaderContent className="header-right">
          <DivStudyTimeContainer>
            <DivStudyTime>
              <h3>나의 공부 시간</h3>
              <h1>
                {studyTime1.day}
                <span>Day </span>
                {studyTime1.hour}
                <span>Hour </span>
                {studyTime1.minute}
                <span>Min </span>
                {studyTime1.second}
                <span>Sec </span>
              </h1>
            </DivStudyTime>
            <DivStudyTime>
              <h3>상대방 공부 시간</h3>
              <h1>
                {studyTime2.day}
                <span>Day </span>
                {studyTime2.hour}
                <span>Hour </span>
                {studyTime2.minute}
                <span>Min </span>
                {studyTime2.second}
                <span>Sec </span>
              </h1>
            </DivStudyTime>
          </DivStudyTimeContainer>
          <DivStudyTimeDiff>
            <h3>공부 시간 차이</h3>
            <h3>
              <h1>
                {moreThan ? null : "-"}
                {studyTimeGap.day}
                <span>Day </span>
                {studyTimeGap.hour}
                <span>Hour </span>
                {studyTimeGap.minute}
                <span>Min </span>
                {studyTimeGap.second}
                <span>Sec </span>
              </h1>
            </h3>
          </DivStudyTimeDiff>
        </DivSimilarityHeaderContent>
      </DivUserSimilarityHeaderContainer>
      <DivChartContainer>
        {!labels || (!dataList1 && !dataList2) ? (
          <h1>
            아직 비교할 피드가 없습니다. 지금 바로 피드를 작성하러 갑시다.
          </h1>
        ) : (
          <ChartBarVertical
            labels={labels}
            data1={dataList1}
            data2={dataList2}
          />
        )}
      </DivChartContainer>
    </DivUserSimilarityContainer>
  );
};

export default UserSimilarity;

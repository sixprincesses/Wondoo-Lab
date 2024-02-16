import { useEffect, useMemo, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import useGetStreak from "../../apis/member/useGetStreak";
import { color1, color2, color3, colorB, colorW } from "../../constants/colors";
import {
  DivStreakContainer,
  DivStreakCountStyled,
  SvgStreakStyled,
} from "./ChartStreakStyle";

interface DayListItem {
  date: Date;
  count: number;
  x: number;
  y: number;
  color: string;
  colorStroke: string;
}

interface StreakData {
  date: Date;
  count: number;
}

const ChartStreak = () => {
  // 오늘을 기준으로 변수들 초기화
  const today = useMemo(() => new Date(), []);
  const before1Year = today.getFullYear() - 1;
  const thisMonth = today.getMonth();
  const thisDate = today.getDate();
  const firstDayOfThisYear = new Date(
    before1Year,
    thisMonth,
    thisDate + 1
  ).getDay();

  // rect의 속성들 초깃값 세팅
  const nx = useRef(5);
  const ny = useRef(5 + (firstDayOfThisYear - 1) * 18);
  const isMonthChangeList: number[] = useMemo(() => [], []);
  let firstMonth = thisMonth + 1;
  const currentMonth = useRef(thisMonth);

  const { memberId } = useParams();
  const getStreak = useGetStreak();

  // 오늘부터 1년간의 빈 스트릭 생성
  const dayList: DayListItem[] = useMemo(() => {
    const newList = [];
    for (let i = 1; ; i++) {
      let idx = i;
      idx += firstDayOfThisYear;
      nx.current = idx !== 1 && idx % 7 === 1 ? nx.current + 18 : nx.current;
      ny.current = idx % 7 === 1 ? 5 : ny.current + 18;

      const newDate = new Date(before1Year, thisMonth, thisDate + i);

      if (newDate.getMonth() !== currentMonth.current) {
        isMonthChangeList.push(nx.current);
        currentMonth.current = newDate.getMonth();
      }

      newList.push({
        date: newDate,
        count: 0,
        x: nx.current,
        y: ny.current,
        color: colorW,
        colorStroke: color1,
      });
      if (
        newDate.getFullYear() === today.getFullYear() &&
        newDate.getMonth() === today.getMonth() &&
        newDate.getDate() === today.getDate()
      ) {
        break;
      }
    }
    return newList;
  }, [
    before1Year,
    firstDayOfThisYear,
    today,
    thisDate,
    thisMonth,
    isMonthChangeList,
  ]);

  nx.current = 5;
  ny.current = 5 + (firstDayOfThisYear - 1) * 18;

  // 입력받은 데이터들
  const dataList = useRef<StreakData[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [streakCount, setStreakCount] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      if (memberId === undefined) return;
      const newData = await getStreak(+memberId).finally(() =>
        setLoading(false)
      );

      dataList.current = newData.grass;
      setStreakCount(newData.current_streak);
      // console.log(dataList.current);
      // console.log(newData.current_streak);
    };

    fetchData();
  }, []);

  // 입력받은 데이터들 각각의 날짜에 count 넣어서 업데이트 해주기
  dataList.current.forEach((data) => {
    const foundSameDateIndex = dayList.findIndex((item: DayListItem) => {
      const itemDate = item.date;
      const targetDate = new Date(data.date);

      return (
        itemDate.getFullYear() === targetDate.getFullYear() &&
        itemDate.getMonth() === targetDate.getMonth() &&
        itemDate.getDate() === targetDate.getDate()
      );
    });

    if (foundSameDateIndex !== -1) {
      let color = "";
      let colorStroke = "";
      switch (data.count) {
        case 0:
          color = colorW;
          colorStroke = color1;
          break;
        case 1:
          color = color1;
          colorStroke = color1;
          break;
        case 2:
        case 3:
          color = color2;
          colorStroke = color2;
          break;
        case 4:
        case 5:
          color = color3;
          colorStroke = color3;
          break;
        default:
          color = colorB;
          colorStroke = colorB;
          break;
      }
      dayList[foundSameDateIndex] = {
        ...dayList[foundSameDateIndex],
        color: color,
        colorStroke: colorStroke,
        count: data.count,
      };
    }
  });

  if (loading) {
    return <></>;
  }

  return (
    <DivStreakContainer>
      <DivStreakCountStyled>
        <h2>스트릭 {streakCount}일</h2>
      </DivStreakCountStyled>
      <SvgStreakStyled width={"3%"}>
        <text fontSize="13" x="15" y="15">
          일
        </text>
        <text fontSize="13" x="15" y="33">
          월
        </text>
        <text fontSize="13" x="15" y="51">
          화
        </text>
        <text fontSize="13" x="15" y="70">
          수
        </text>
        <text fontSize="13" x="15" y="87">
          목
        </text>
        <text fontSize="13" x="15" y="105">
          금
        </text>
        <text fontSize="13" x="15" y="123">
          토
        </text>
      </SvgStreakStyled>
      <SvgStreakStyled width={"95%"}>
        {dayList.map((rectangle, idx) => {
          return (
            <rect
              onMouseUp={() => console.log(rectangle.date)}
              x={rectangle.x}
              y={rectangle.y}
              width="13"
              height="13"
              ry="2"
              fill={rectangle.color}
              stroke={rectangle.colorStroke}
              strokeWidth="2"
              key={idx}
            />
          );
        })}
        {isMonthChangeList.map((x, idx) => (
          <text x={x} y={145} key={idx}>
            {++firstMonth % 13 ? firstMonth % 13 : (firstMonth++ % 13) + 1}월
          </text>
        ))}
      </SvgStreakStyled>
    </DivStreakContainer>
  );
};

export default ChartStreak;

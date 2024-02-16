import {
  Chart as ChartJS,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
} from "chart.js";
import { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import { useParams } from "react-router-dom";
import useGetStudyTime from "../../apis/member/useGetStudyTime";
import { color1 } from "../../constants/colors";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

interface MonthStatistics {
  year_month: string;
  total_time: number;
}

const ChartLine = () => {
  const [labels, setLabels] = useState<string[]>([]);
  const [dataList, setDataList] = useState<number[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const getStudyTime = useGetStudyTime();
  const { memberId } = useParams();
  const options = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          stepSize: 1,
        },
      },
    },
  };

  const [data, setData] = useState({
    labels: ["React", "Algorithm", "OS", "TypeScript", "JavaScript", "Axios"],
    datasets: [
      {
        label: "공부 시간",
        data: [1, 2, 3, 4, 5, 6],
        borderColor: color1,
        backgroundColor: color1,
      },
    ],
  });

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      if (memberId === undefined) return;
      const newDataList: MonthStatistics[] = await getStudyTime(
        +memberId
      ).finally(() => setIsLoading(false));

      setLabels(
        newDataList
          .map((data) => {
            return new Date(data.year_month).getMonth() + 1 + "월";
          })
          .reverse()
      );

      setDataList(
        newDataList.map((data) => {
          return Math.floor(data.total_time / 60);
        })
      );
    };

    fetchData();
  }, []);

  useEffect(() => {
    setData({
      labels,
      datasets: [
        {
          label: "공부 시간",
          data: dataList,
          borderColor: color1,
          backgroundColor: color1,
        },
      ],
    });
  }, [labels, dataList]);

  if (isLoading) {
    return <></>;
  }
  return <Line options={options} data={data} />;
};

export default ChartLine;

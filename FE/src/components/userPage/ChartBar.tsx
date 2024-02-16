import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import { useParams } from "react-router-dom";
import useGetKeywordCount from "../../apis/member/useGetKeywordCount";
import { color1 } from "../../constants/colors";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

interface KeywordCount {
  name: string;
  count: number;
}

const BarChart = () => {
  const [labels, setLabels] = useState<string[]>([]);
  const [dataList, setDataList] = useState<number[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const getKeywordCount = useGetKeywordCount();
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
    labels: ["a", "b", "c", "d", "e", "f"],
    datasets: [
      {
        label: "공부 시간",
        data: [100, 124, 67, 250, 370, 354],
        backgroundColor: color1,
      },
    ],
  });

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      if (memberId === undefined) return;
      const newDataList: KeywordCount[] = await getKeywordCount({
        member_id: +memberId,
      }).finally(() => setIsLoading(false));

      setLabels(
        newDataList.map((data) => {
          return data.name;
        })
      );

      setDataList(
        newDataList.map((data) => {
          return data.count;
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
          label: "태그 횟수",
          data: dataList,
          backgroundColor: color1,
        },
      ],
    });
  }, [labels, dataList]);

  if (isLoading) {
    return <></>;
  }
  return <Bar options={options} data={data}></Bar>;
};

export default BarChart;

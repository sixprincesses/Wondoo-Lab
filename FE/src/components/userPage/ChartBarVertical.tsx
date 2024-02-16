import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";
import { color1, color2 } from "../../constants/colors";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

interface CharBarVerticalProps {
  labels: string[];
  data1: number[];
  data2: number[];
}

const ChartBarVertical = ({ labels, data1, data2 }: CharBarVerticalProps) => {
  const options = {
    indexAxis: "y" as const,
    elements: {
      bar: {
        borderWidth: 2,
      },
    },
    responsive: true,
    plugins: {
      legend: {
        position: "top" as const,
        align: "end" as const,
      },
    },
    scales: {
      x: {
        beginAtZero: true,
        ticks: {
          stepSize: 1,
        },
      },
    },
  };

  const data = {
    labels,
    datasets: [
      {
        label: "나",
        data: data1,
        borderColor: color1,
        backgroundColor: color1,
      },
      {
        label: "상대방",
        data: data2,
        borderColor: color2,
        backgroundColor: color2,
      },
    ],
  };

  return <Bar options={options} data={data} />;
};

export default ChartBarVertical;

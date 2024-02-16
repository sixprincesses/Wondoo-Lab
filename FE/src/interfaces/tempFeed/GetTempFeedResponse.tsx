import { timeLog } from "./TempFeedState";

interface GetTempFeedRespons {
  member_id: number;
  title: string;
  content: string;
  time_logs: timeLog[];
  total_time: number;
  keywords: string[];
}

export type { GetTempFeedRespons };

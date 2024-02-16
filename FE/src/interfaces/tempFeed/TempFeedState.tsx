interface timeLog {
  startTime: Date;
  endTime: Date;
}

interface data {
  title: string;
  keywords: string[];
  instanceData: string;
  timelogs: timeLog[];
  totalTime: number;
}

interface tempFeedState {
  data: data;
  startTime: Date;
  running: boolean;
}

export type { tempFeedState, timeLog };

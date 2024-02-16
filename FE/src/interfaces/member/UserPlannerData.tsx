interface PlanDetail {
  title: string;
  time: number;
}

interface SubjectListByDay {
  index: number;
  plan_detail: PlanDetail[];
}

interface FetchedPlannerData {
  id: string;
  memberId: number;
  year: number;
  month: number;
  date: Date;
  planDetails: PlanDetail[];
}

export type { PlanDetail, SubjectListByDay, FetchedPlannerData };

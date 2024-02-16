import { content } from "./FollowState";

interface pageable {
  pageNumber: number;
}

interface getFollowersResponse {
  content: content[];
  pageable: pageable;
  last: boolean;
}

export type { getFollowersResponse };

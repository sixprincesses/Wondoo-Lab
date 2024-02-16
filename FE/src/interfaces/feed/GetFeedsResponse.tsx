import { feed } from "./FeedState";

interface GetFeedsResponse {
  feeds: feed[];
  last_feed_id: number;
}

export type { GetFeedsResponse };

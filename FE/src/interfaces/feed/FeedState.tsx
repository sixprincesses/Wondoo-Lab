import { timeLog } from "../tempFeed/TempFeedState";

interface member {
  member_id: number;
  nickname: string;
  image_id: string;
  level: string;
}

interface rank extends member {
  score: number;
}

interface feed {
  feed_id: number;
  member: member;
  title: string;
  content: string;
  time_logs: timeLog[];
  total_time: number;
  created_time: Date;
  updated_time: Date;
  keywords: string[];
  is_liked: boolean;
  is_bookmarked: boolean;
}

interface reply {
  comment_id: number;
  content: string;
  is_deleted: boolean;
  member: member;
  feed_id: number;
  createdTime: Date;
  updatedTime: Date;
}

interface comment {
  comment_id: number;
  content: string;
  deleted: boolean;
  feed_id: number;
  is_deleted: boolean;
  member: member;
  parent_id: number;
  replies: reply[];
  created_time: Date;
  updated_time: Date;
}

interface keyword {
  name: string;
  rank: number;
  count: number;
  gap: number;
}

interface feedState {
  feeds: {
    feeds: feed[];
    last_feed_id: number | null;
    isEnd: boolean;
    isFetching: boolean;
  };
  selectedFeed: feed;
  selectedFeedComments: comment[];
  keywordRanks: keyword[];
}

export type { comment, feed, feedState, keyword, member, rank, reply };

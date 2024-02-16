import { bookmark } from "../feed/Bookmark";
import { feed, rank } from "../feed/FeedState";

interface memberInfo {
  member_id: number;
  image_id: string;
  cover_image_id: string;
  nickname: string;
  name: string;
  email: string;
  phone: string;
  gender: string;
  is_follow: boolean;
  created_time: Date;
  follower_count: number;
  following_count: number;
  level: string;
}

interface memberState {
  memberInfo: memberInfo;
  feed: {
    memberFeeds: feed[];
    last_feed_id: number | null;
    isEnd: boolean;
    isFetching: boolean;
  };
  bookmark: {
    bookmarks: bookmark[];
    last_bookmark_id: number | null;
    isEnd: boolean;
    isFetching: boolean;
  };
  rank: rank[];
}

export type { memberInfo, memberState };

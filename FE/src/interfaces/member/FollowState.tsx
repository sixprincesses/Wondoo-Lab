interface content {
  member_id: number;
  image_id: string;
  nickname: string;
  level: string;
}

interface followInfo {
  members: content[];
  isEnd: boolean;
  isFetching: boolean;
  page: number;
  size: number;
}

interface followState {
  followers: followInfo;
  followings: followInfo;
  friends: followInfo;
}

export type { content, followInfo, followState };

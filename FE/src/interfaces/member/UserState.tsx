interface userInfo {
  member_id: number;
  image_id: string;
  nickname: string;
  email: string;
  name: string;
  phone: string;
  gender: string;
  level: string;
  access_token: string;
  refresh_token: string;
}

interface userState {
  userInfo: userInfo;
}

export type { userInfo, userState };

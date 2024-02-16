interface LoginResponse {
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
  lastfeed: number;
}

export type { LoginResponse };

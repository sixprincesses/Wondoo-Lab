interface bookmark {
  bookmark_id: number;
  feed_id: number;
  member: {
    member_id: number;
    nickname: string;
    image_id: string;
  };
  title: string;
  created_time: Date;
}

export type { bookmark };

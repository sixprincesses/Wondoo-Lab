interface dummyChat {
  content: string;
  createdTime: Date;
  memberId: number;
  messageType: string;
  nickname: string;
  reference: string;
  image_id: string;
}

const dummyChats: dummyChat[] = [
  {
    content: "밥은 먹고 다니냐 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 15:55:11"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "형이 댓글 한사발 말아놨다 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 15:55:31"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "야 ㅋㅋ 댓글뭐냐 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 16:21:20"),
    memberId: 0,
    messageType: "MESSAGE_TXT",
    nickname: "KTeaGyu",
    reference: "abc",
    image_id: "",
  },
  {
    content: "형이 코드 맛있게 짜놨다",
    createdTime: new Date("Jan 24 2024 16:22:11"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "보고 배워라 ㅋ",
    createdTime: new Date("Jan 24 2024 16:22:35"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "오 코드 맛있는데?",
    createdTime: new Date("Jan 24 2024 16:22:57"),
    memberId: 0,
    messageType: "MESSAGE_TXT",
    nickname: "KTeaGyu",
    reference: "abc",
    image_id: "",
  },
  {
    content: "밥은 먹고 다니냐 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 15:55:11"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "형이 댓글 한사발 말아놨다 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 15:55:31"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "야 ㅋㅋ 댓글뭐냐 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 16:21:20"),
    memberId: 0,
    messageType: "MESSAGE_TXT",
    nickname: "KTeaGyu",
    reference: "abc",
    image_id: "",
  },
  {
    content: "형이 코드 맛있게 짜놨다",
    createdTime: new Date("Jan 24 2024 16:22:11"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "보고 배워라 ㅋ",
    createdTime: new Date("Jan 24 2024 16:22:35"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "오 코드 맛있는데?",
    createdTime: new Date("Jan 24 2024 16:22:57"),
    memberId: 0,
    messageType: "MESSAGE_TXT",
    nickname: "KTeaGyu",
    reference: "abc",
    image_id: "",
  },
  {
    content: "밥은 먹고 다니냐 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 15:55:11"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "형이 댓글 한사발 말아놨다 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 15:55:31"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "야 ㅋㅋ 댓글뭐냐 ㅋㅋ",
    createdTime: new Date("Jan 24 2024 16:21:20"),
    memberId: 0,
    messageType: "MESSAGE_TXT",
    nickname: "KTeaGyu",
    reference: "abc",
    image_id: "",
  },
  {
    content: "형이 코드 맛있게 짜놨다",
    createdTime: new Date("Jan 24 2024 16:22:11"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "보고 배워라 ㅋ",
    createdTime: new Date("Jan 24 2024 16:22:35"),
    memberId: 1,
    messageType: "MESSAGE_TXT",
    nickname: "Oh Hee Juice",
    reference: "abc",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    content: "오 코드 맛있는데?",
    createdTime: new Date("Jan 24 2024 16:22:57"),
    memberId: 0,
    messageType: "MESSAGE_TXT",
    nickname: "KTeaGyu",
    reference: "abc",
    image_id: "",
  },
];

export { dummyChats };
export type { dummyChat };

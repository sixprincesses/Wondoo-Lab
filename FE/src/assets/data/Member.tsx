interface dummyMember {
  social_id: number;
  social_nickname: string;
  name: string;
  phone: string;
  email: string;
  gender: string;
  image_id: string;
}

const dummyMembers: dummyMember[] = [
  {
    social_id: 0,
    social_nickname: "KTaeGyu",
    name: "김태규",
    phone: "010-9753-1677",
    email: "xorb269@naver.com",
    gender: "남성",
    image_id: "",
  },
  {
    social_id: 1,
    social_nickname: "Oh Hee Juice",
    name: "오희주",
    phone: "010-2993-5457",
    email: "ohiju@gmail.com",
    gender: "남성",
    image_id:
      "https://thumb.mtstarnews.com/06/2021/07/2021073005413626181_1.jpg/dims/optimize",
  },
  {
    social_id: 2,
    social_nickname: "DaeJangGongJu",
    name: "최준호",
    phone: "010-3535-6778",
    email: "imprincess77@gmail.com",
    gender: "남성?",
    image_id:
      "https://post-phinf.pstatic.net/MjAyMDA2MzBfMjUx/MDAxNTkzNTA3MzM1OTQ0.NhkrltVXOGoU64X1LN9vgQ-FuOm0BU7ttl1p6--A9c0g._s61B7ydmg44idHIeDYqGosvQuyldcmsN28qD8dC1TYg.JPEG/img_20200629174247_c16737be.jpg?type=w800_q75",
  },
  {
    social_id: 3,
    social_nickname: "SnowWhite",
    name: "윤설",
    phone: "010-3369-4544",
    email: "snowwhite1774@gmail.com",
    gender: "여성",
    image_id:
      "https://i.pinimg.com/550x/a3/44/bb/a344bbe237927441ee70280a0fbe6349.jpg",
  },
  {
    social_id: 4,
    social_nickname: "Hot-ttu",
    name: "황주영",
    phone: "010-1581-5441",
    email: "hotttu486@gmail.com",
    gender: "여성?",
    image_id:
      "https://i.pinimg.com/originals/ed/f9/5e/edf95eaee1fc40692082b5633bc8c060.jpg",
  },
  {
    social_id: 5,
    social_nickname: "JK Kim",
    name: "김정욱",
    phone: "010-1241-4441",
    email: "hangay@gmail.com",
    gender: "게이",
    image_id:
      "https://i.namu.wiki/i/o3E3OITjZpUQJY4b3OiBrTL2l26PaMKdahuPavfKB1VTMtRVrzPlGZBCiJfpJz-9ObDxiiatoCgtJ5_AqF7LEg.webp",
  },
];

export { dummyMembers };
export type { dummyMember };

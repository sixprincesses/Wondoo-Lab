interface diary {
  date: string;
  content: string;
  repositoryCount: number;
  commitCount: number;
  log: LogRepository[];
}

interface LogRepository {
  name: string;
  branchList: LogBranch[];
}

interface LogBranch {
  name: string;
  commitList: LogCommit[];
}

interface LogCommit {
  time: Date;
  commitContent: string;
}

const dummyDiary: diary[] = [
  {
    date: "2023.01.23",
    content: `
    오늘은 개발 관련하여 여러 가지 작업을 진행했습니다.
    아침에 일어나자마자, AWS 서버에 문제가 발생했음을 알게 되었습니다. 서버 문제는 서비스에 큰 지장을 줄 수 있기 때문에, 빠르게 문제점을 파악하여 수정하였습니다.
    그 후에, API의 재시도 기능을 구현하는 작업에 들어갔습니다. 이 기능은 일시적인 오류로 인해 API 호출이 실패할 경우, 자동으로 다시 시도하도록 하는 기능입니다. 이를 통해 서비스의 안정성을 높일 수 있을 것입니다.
    오후에는 잔액과 관련된 새로운 API를 구현했습니다. 이 API는 사용자의 잔액 정보를 제공하며, 사용자에게 더욱 유용한 정보를 제공할 수 있게 되었습니다.
  `,
    repositoryCount: 2,
    commitCount: 16,
    log: [
      {
        name: "frontend",
        branchList: [
          {
            name: "main",
            commitList: [
              {
                time: new Date(2024, 0, 13, 13, 11),
                commitContent: "Fix: 이상한거 고침",
              },
              {
                time: new Date(2024, 0, 13, 14, 13),
                commitContent: "Feat: 뭔가 만들었음",
              },
              {
                time: new Date(2024, 0, 13, 15, 1),
                commitContent: "Merge: 뭔가 합쳤음",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    date: "2023.01.22",
    content: `
    즐거웠습니다.
  `,
    repositoryCount: 2,
    commitCount: 16,
    log: [
      {
        name: "frontend",
        branchList: [
          {
            name: "main",
            commitList: [
              {
                time: new Date(2024, 0, 13, 13, 11),
                commitContent: "Fix: 이상한거 고침",
              },
              {
                time: new Date(2024, 0, 13, 14, 13),
                commitContent: "Feat: 뭔가 만들었음",
              },
              {
                time: new Date(2024, 0, 13, 15, 1),
                commitContent: "Merge: 뭔가 합쳤음",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    date: "2023.01.21",
    content: `
    이거까진 안 열어보겠지?
  `,
    repositoryCount: 2,
    commitCount: 16,
    log: [
      {
        name: "frontend",
        branchList: [
          {
            name: "main",
            commitList: [
              {
                time: new Date(2024, 0, 13, 13, 11),
                commitContent: "Fix: 이상한거 고침",
              },
              {
                time: new Date(2024, 0, 13, 14, 13),
                commitContent: "Feat: 뭔가 만들었음",
              },
              {
                time: new Date(2024, 0, 13, 15, 1),
                commitContent: "Merge: 뭔가 합쳤음",
              },
            ],
          },
        ],
      },
    ],
  },
];

export { dummyDiary };
export type { diary };

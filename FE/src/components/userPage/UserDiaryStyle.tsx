import { color3, colorG, colorW, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivUserDiaryContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 10px auto;
  width: 1020px;
`;

const DivButtonAndInfoContainer = styled.div`
  width: 100%;
  height: 50px;
  background-color: ${colorWW};
  border-radius: 5px;

  display: flex;

  margin: 30px auto;

  p {
    margin: auto 0;
  }

  img {
    width: 30px;
    height: 30px;

    margin: auto 30px;
  }
`;

const ButtonCreateDiary = styled.div`
  border: 3px solid ${color3};
  border-radius: 5px;

  color: ${colorW};
  background-color: ${color3};

  height: 40px;
  width: 150px;

  margin: auto 10px auto auto;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    cursor: pointer;
  }
`;

const DivDiaryContainer = styled.div`
  width: 100%;
  margin: 30px auto;
`;

const DivDiaryHeader = styled.div`
  display: flex;
  margin: auto;
  border: 2px solid ${colorG};
  border-radius: 10px;
  width: 100%;
  height: 60px;
  background-color: ${colorWW};

  h3 {
    margin: auto auto auto 20px;
    &:hover {
      cursor: pointer;
      color: ${colorG};
    }
  }
`;

const DivDiaryHeaderRight = styled.div`
  display: flex;
  * {
    margin: auto 20px auto 0;
  }

  .log:hover {
    cursor: pointer;
    color: ${colorG};
  }

  img {
    width: 20px;
    &:hover {
      cursor: pointer;
    }
  }
`;

const DivDiaryContentContainer = styled.div`
  width: 100%;
  display: flex;
`;

const DivDiaryContent = styled.div`
  margin: 0 auto;
  border: 2px solid ${colorG};
  background-color: ${colorWW};
  border-top: none;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  width: 100%;
  height: 200px;
  padding: 30px;
  overflow-y: auto;

  &.is-log-on {
    width: 50%;
  }
`;

const DivGitLogContent = styled.div`
  width: 50%;

  height: 200px;

  border: 2px solid ${colorG};
  border-top: none;
  border-left: none;
  border-bottom-right-radius: 10px;
  background-color: ${colorWW};

  overflow-y: auto;
`;

const DivRepoName = styled.div`
  img {
    height: 15px;
    width: 15px;
  }
`;

const DivCommitContainer = styled.div`
  padding: 5px 0 5px 15px;
`;

const DivCommitHeader = styled.div`
  img {
    height: 15px;
  }
`;

const DivCommitContent = styled.div`
  width: 100%;
  border: 2px solid ${colorG};
  border-radius: 10px;
  padding: 10px 10px;

  img {
    margin: auto 0 auto auto;
  }
`;

export {
  DivUserDiaryContainer,
  DivButtonAndInfoContainer,
  ButtonCreateDiary,
  DivDiaryContainer,
  DivDiaryHeader,
  DivDiaryHeaderRight,
  DivDiaryContentContainer,
  DivDiaryContent,
  DivGitLogContent,
  DivRepoName,
  DivCommitContainer,
  DivCommitHeader,
  DivCommitContent,
};

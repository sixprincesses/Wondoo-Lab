import { Global, css } from "@emotion/react";
import { color1, colorB, colorG, colorW, colorDG } from "../constants/colors";

const styles = css`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  html,
  body,
  #root,
  #root > div {
    background: ${colorDG};
    height: 100vh;
  }

  html,
  body {
    // 스크롤바 설정
    & ::-webkit-scrollbar {
      width: 10px;
      height: 10px;
    }
    & ::-webkit-scrollbar-thumb {
      background: ${color1};
      border-radius: 10px;
    }
    & ::-webkit-scrollbar-track {
      background: transparent;
    }
  }

  a {
    color: ${colorB};
    &:link {
      text-decoration: none;
    }
    &:visited,
    &:active {
      color: ${colorB};
    }
  }
  // 모달 input 창 스타일링
  .swal2-input {
    width: 200px;
    height: 50px;
    margin: 20px auto;
  }

  // 깃허브 커밋 관련 스타일링
  .githubData {
    display: none;
  }
  .github {
    display: flex;
    flex-direction: column;
    justify-content: start;
    background: ${colorG};
    border-radius: 5px;
    margin: 10px 0;
  }
  .githubHeader {
    display: flex;
    align-items: center;
    justify-content: start;
    padding: 10px 5px 5px 10px;
    color: ${colorB};
    font-weight: 600;
  }
  .githubBody {
    display: flex;
    flex-direction: column;
    gap: 10px;
    padding: 0 5px 5px 5px;
  }
  .file {
    border: 3px solid ${color1};
    border-radius: 10px;
    display: grid;
    grid-template-rows: 25px 1fr;
    overflow: hidden;
  }
  .fileName {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 0 10px;
    background: ${colorW};
    font-size: small;
  }
  .fileContent {
    font-weight: 600;
    font-size: 12px;
    color: black;
    background: ${colorW};
    & .line-info {
      background: #bbdfff;
      line-height: 30px;
      & p:nth-last-of-type(-n + 2) {
        background: #ddf4ff;
      }
    }
    & .add {
      background: #ccffd8;
      & p:nth-last-of-type(-n + 2) {
        background: #e6ffec;
      }
    }
    & .sub {
      background: #ffd7d5;
      & p:nth-last-of-type(-n + 2) {
        background: #ffebe9;
      }
    }
  }
  .line {
    display: grid;
    grid-template-columns: 40px 40px 22px 1fr;
    line-height: 20px;
    & p {
      margin: 0;
    }
    & p:nth-of-type(-n + 2) {
      padding: 0 5px;
      text-align: right;
    }
    & p:nth-of-type(3) {
      text-align: center;
      font-weight: 400;
    }
    & p:nth-of-type(4) {
      text-align: start;
    }
  }
`;

const GlobalStyle = () => {
  return <Global styles={styles} />;
};

export default GlobalStyle;

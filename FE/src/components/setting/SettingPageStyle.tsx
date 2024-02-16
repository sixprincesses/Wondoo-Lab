import { color1, colorB, colorG, colorW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivSettingContainer = styled.div`
  .title {
    margin: 30px auto;
    box-shadow: 5px 5px 5px ${colorG};
  }
  display: grid;
  grid-template-columns: 1fr 850px 1fr;
`;

const DivSettingInputContainer = styled.div`
  min-height: 750px;
`;

const DivSettingInput = styled.div<{ height: string }>`
  margin: 50px auto 10px auto;
  border: 1px solid ${color1};
  border-radius: 10px;
  background-color: ${colorW};

  height: ${(props) => props.height};
  display: flex;

  box-shadow: 5px 5px 5px ${colorG};

  .theme-div {
    margin: auto;
    padding: 10px;

    &:hover {
      border: 1px solid ${colorB};
      border-radius: 10px;
    }
  }

  .isSelected {
    border: 2px solid ${colorB};
    border-radius: 10px;
  }

  .infoSettingLabel {
    display: block;
    margin: auto 0 auto 15px;
    width: 15%;
  }

  .infoSettingInput {
    border: none;
    border-radius: 5px;

    background-color: ${colorG};

    width: 67.5%;
    height: 70%;

    margin: auto 0;
    padding: 10px;
  }

  .theme-image {
    margin: 30px auto;
    width: 360px;
    height: 260px;

    border: 2px solid ${colorG};
    border-radius: 10px;
  }
`;

const ButtonInfoSubmit = styled.button`
  border: none;
  border-radius: 5px;

  background-color: ${color1};

  height: 50px;
  width: 100px;

  margin: 20px 375px;

  &:hover {
    cursor: pointer;
  }
`;

const DivSettingAlarmText = styled.div`
  margin: auto auto auto 20px;
  h2 {
    margin-bottom: 10px;
  }
`;

const SpanSettingErrorMsg = styled.div`
  color: red;
  margin: 0 20px;
`;

export {
  DivSettingContainer,
  DivSettingInputContainer,
  DivSettingInput,
  ButtonInfoSubmit,
  DivSettingAlarmText,
  SpanSettingErrorMsg,
};

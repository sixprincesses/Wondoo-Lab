import {
  color2,
  color3,
  colorG,
  colorW,
  colorWW,
} from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivUserPlansContainer = styled.div`
  width: 1020px;
  margin: auto;
`;

const DivUserPlansContent = styled.div`
  background-color: ${colorWW};
  width: 100%;
  border-radius: 5px;
  margin: 30px 0;
  padding: 30px;
`;

const DivTimeCountInputContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  margin: 10px;
`;

const DivTimeCountInput = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 200px;
  height: 50px;

  color: ${color3};

  .input-time-count {
    width: 100%;
    height: 100%;

    border: none;
    border-bottom: 3px solid ${color3};

    text-align: center;

    color: ${color3};
  }
`;

const ButtonInPlansPage = styled.div<{ width: string }>`
  border: 3px solid ${color3};
  border-radius: 5px;

  color: ${colorW};
  background-color: ${color3};

  height: 40px;
  width: ${(props) => props.width};

  margin: auto 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    cursor: pointer;
  }

  &.right {
    margin-right: 0;
  }
`;

const DivTimetableList = styled.div`
  display: flex;
`;

const DivTimetable = styled.div`
  margin: 20px auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
  * {
    border: none;
    box-shadow: 5px 5px 10px ${colorG};
    width: 100px;
  }
`;

const DivDayIndex = styled.div`
  display: flex;
  justify-content: center;
`;

const DivSubjectContainer = styled.div<{ timeCount: number }>`
  height: ${(props) => `${props.timeCount * 50}px`};
  background-color: ${colorWW};
  border-radius: 3px;
`;

const DivSubject = styled.div<{ timeCount: number }>`
  border: 2px solid ${color2};
  border-left: 5px solid ${color2};
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  height: ${(props) => `${props.timeCount * 50}px`};
  text-align: center;
  background-color: ${colorW};
  overflow: hidden;
  .title {
    box-shadow: none;
    background-color: ${colorW};
    margin: 20px auto;
    max-width: 50px;
  }
`;

const DivNotSubject = styled.div<{ timeCount: number }>`
  height: ${(props) => `${props.timeCount * 50}px`};
`;

const ButtonAddSubject = styled.button`
  height: 50px;
  font-size: 25px;
  color: ${colorW};
  background-color: ${color3};
  border-radius: 3px;
`;

export {
  DivUserPlansContainer,
  DivUserPlansContent,
  DivTimetableList,
  DivTimetable,
  DivDayIndex,
  DivTimeCountInputContainer,
  DivTimeCountInput,
  DivSubjectContainer,
  DivSubject,
  DivNotSubject,
  ButtonInPlansPage,
  ButtonAddSubject,
};

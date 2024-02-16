import styled from "@emotion/styled";
import {
  color1,
  color2,
  color3,
  colorG,
  colorNG,
  colorW,
  colorWW,
} from "../../constants/colors.ts";

const StudyContainer = styled.div`
  z-index: 1;
  position: relative;
  width: 100%;
  min-width: 1000px;
  height: 100%;
  min-height: 600px;
  max-height: 100vh;
  border: 3px solid ${colorG};
  background-color: ${colorNG};
  border-radius: 10px;
  display: grid;
  grid-template-rows: 50px 1fr;
  grid-template-columns: 1fr 1fr;
  overflow: hidden;
`;
const StudyHeader = styled.h2`
  grid-row: 1/2;
  grid-column: 1/3;
  background: ${colorWW};
  border-bottom: 2px solid ${colorG};
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
`;
const ToHome = styled.span`
  cursor: pointer;
  margin-right: 10px;
  &:hover {
    opacity: 0.7;
  }
`;
const StudyInput = styled.div`
  grid-row: 2/3;
  grid-column: 1/2;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: start;
  gap: 10px;
  border-right: 2px solid ${colorG};
  margin: 20px 0;
  margin-bottom: 50px;
  overflow-y: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
`;
const InstanceBox = styled.div`
  width: 95%;
  border: 2px solid ${colorG};
  border-radius: 10px;

  &.dragging {
    opacity: 0.5;
  }
`;
const StudyOutput = styled.div`
  grid-row: 2/3;
  grid-column: 2/3;
  margin: 20px 0;
  margin-bottom: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: start;
  background: ${colorW};
  overflow-y: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  & > div {
    width: 95%;
  }
`;
const StudyFooter = styled.div`
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 3px;
  padding: 12px 13px;
`;
const StudyFooterBtns = styled.div`
  display: flex;
  align-items: center;
  justify-content: end;
  gap: 20px;
  & > div {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 80px;
    height: 40px;
    border-radius: 10px;
    font-weight: 600;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
`;
const StudyCancle = styled.div`
  border: 3px solid ${color3};
  color: ${color3};
`;
const StudyConfirm = styled.div`
  background: ${color3};
  color: white;
`;

// Stopwatch
const WatchIcon = styled.div`
  width: 50px;
  height: 50px;
  background-color: #3498db;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
  margin-right: 20px;
`;

const InstanceAdder = styled.div`
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  color: ${color2};
  font-size: 28px;
  border: 1px solid ${color2};
  padding-bottom: 5px;
  cursor: pointer;
  outline: none;

  &:hover {
    background-color: ${color1};
    color: #fff;
  }
`;

const WatchContainer = styled.div`
  width: 180px;
  margin-left: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 35px;
`;

export {
  InstanceAdder,
  InstanceBox,
  StudyCancle,
  StudyConfirm,
  StudyContainer,
  StudyFooter,
  StudyFooterBtns,
  StudyHeader,
  StudyInput,
  StudyOutput,
  ToHome,
  WatchContainer,
  WatchIcon,
};

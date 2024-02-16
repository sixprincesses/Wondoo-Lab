import styled from "@emotion/styled";
import { color2, color3, colorW } from "../../constants/colors.ts";

export const WatchButton = styled.div`
  position: relative;
`;

export const TimeLogDisplay = styled.div`
  position: absolute;
  top: 50px;
  left: 0px;
  // transform: translate(-50%, -50%);
  font-size: 15px;
  width: 400px;
  color: ${color2};
  background-color: ${colorW};
  padding: 10px;
  padding-left: 40px;
  border-radius: 5px;
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.4);
`;

export const StudySignal = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 70%;
  left: 30%;
  width: 600px;
  height: 150px;
  background-color: ${color3};
  z-index: 10000;
  border-radius: 2px;
  font-size: 29px;
  color: ${colorW};
`;

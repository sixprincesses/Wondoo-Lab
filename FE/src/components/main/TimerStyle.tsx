import { keyframes } from "@emotion/react";
import styled from "@emotion/styled";

const AppContainer = styled.div`
  display: flex;
  position: relative;
  width: 300px;
  height: 20vh;
  justify-content: center;
  align-items: center;
`;

const foldAnimation = keyframes`
  0% {
    transform: rotateX(0deg);
  }
  100% {
    transform: rotateX(-180deg);
  }
`;

const unfoldAnimation = keyframes`
  0% {
    transform: rotateX(180deg);
  }
  100% {
    transform: rotateX(0deg);
  }
`;

const FlipClockContainer = styled.div`
  display: flex;
  & .Fold {
    animation: ${foldAnimation} 1s cubic-bezier(0.455, 0.03, 0.515, 0.955)
      infinite;
    transform-style: preserve-3d;
    animation-delay: 0.5s;
    top: 0%;
    align-items: flex-end;
    transform-origin: 50% 100%;
    transform: rotateX(0deg);
    background: white;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
    border: 0.5px solid whitesmoke;
    border-bottom: 0.5px solid whitesmoke;
  }
  & .Unfold {
    animation: ${unfoldAnimation} 1s cubic-bezier(0.455, 0.03, 0.515, 0.955)
      infinite;
    transform-style: preserve-3d;
    animation-delay: 0.5s;
    top: 50%;
    align-items: flex-start;
    transform-origin: 50% 0%;
    transform: rotateX(180deg);
    background: white;
    border-bottom-left-radius: 3px;
    border-bottom-right-radius: 3px;
    border: 0.5px solid whitesmoke;
    border-top: 0.5px solid whitesmoke;
  }
  & .Hide {
    display: none;
    transition: opacity 0.5s ease-out;
    opacity: 0;
  }
  & .FlipCard {
    transform-style: preserve-3d;
    transition: transform 0.5s;
    display: flex;
    justify-content: center;
    position: absolute;
    left: 0;
    width: 100%;
    height: 50%;
    overflow: hidden;
    backface-visibility: hidden;
  }
  & .FlipCard span {
    font-family: "Droid Sans Mono", monospace;
    font-weight: lighter;
    color: lighten(black, 20%);
    display: flex;
    align-items: center;
    justify-content: center;
    height: 63.71002%;
    backface-visibility: hidden;
  }
  & .Unfold span {
    transform: translateY(-50%);
  }
  & .Fold span {
    transform: translateY(50%);
  }
  & .UpperCard {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;
    position: absolute;
    backface-visibility: hidden;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  & .LowerCard {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;
    position: absolute;
    backface-visibility: hidden;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
`;

const FlipUnitContainer = styled.div`
  width: 80px;
  height: 60px;
  perspective: 300px;
  background: white;
  border-radius: 3px;
  box-shadow: 0px 10px 10px -10px grey;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const FlipUnitValue = styled.span`
  font-family: "Droid Sans Mono", monospace;
  font-size: 3em;
  font-weight: lighter;
  color: lighten(black, 20%);
  backface-visibility: hidden;
`;

const FlipCard = styled.div``;

const TimerIntervene = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 5px 5px;
`;

const NoData = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export {
  AppContainer,
  FlipCard,
  FlipClockContainer,
  FlipUnitContainer,
  FlipUnitValue,
  NoData,
  TimerIntervene,
};

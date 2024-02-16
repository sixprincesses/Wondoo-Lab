import { keyframes } from "@emotion/react";
import { colorB, colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

interface position {
  top?: string | null;
  right?: string | null;
  bottom?: string | null;
  left?: string | null;
}

interface DropdownWrapperProps {
  position: position;
  width: number;
  height: number;
  lineHeight: number;
  childNum: number;
  isActive: boolean;
}

const Wrapper = styled.div`
  position: relative;
  z-index: 99;
`;

const Background = styled.div<{ isActive: boolean }>`
  z-index: 0;
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: ${(props) => (props.isActive ? "block" : "none")};
`;

const dropdown = (height: number) => keyframes`
  0% {
    display: none;
    height: 0;
  }
  1% {
    display: flex;
    height: 0;
  }
  100% {
    display: flex;
    height: ${height}px;
  }
`;

const rollup = (height: number) => keyframes`
  0% {
    display: flex;
    height: ${height}px;
  }
  99% {
    display: flex;
    height: 0;
  }
  100% {
    display: none;
    height: 0;
  }
`;

const DropdownMenus = styled.div<DropdownWrapperProps>`
  z-index: 1;
  position: absolute;
  top: ${(props) => props.position.top};
  right: ${(props) => props.position.right};
  bottom: ${(props) => props.position.bottom};
  left: ${(props) => props.position.left};
  display: ${(props) => (props.isActive ? "flex" : "none")};
  flex-direction: column;
  align-items: center;
  width: ${(props) => props.width}px;
  height: ${(props) => props.height}px;
  border: 1px solid ${colorG};
  border-radius: 10px;
  color: ${colorB};
  background: ${colorWW};
  overflow: hidden;
  animation: 0.3s
    ${(props) =>
      props.isActive ? dropdown(props.height) : rollup(props.height)};
  & > button {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    border: none;
    background: transparent;
    line-height: ${(props) => props.lineHeight}px;
    cursor: pointer;
  }
  & > :nth-of-type(-n + ${(props) => props.childNum}) {
    border-bottom: 1px solid ${colorG};
  }
`;

export { Background, DropdownMenus, Wrapper };

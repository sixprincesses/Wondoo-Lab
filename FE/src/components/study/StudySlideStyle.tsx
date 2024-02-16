import styled from "styled-components";
import { color1, color3, colorG, colorW, colorWW } from "../../constants/colors";

export const MenuContainer = styled.div<{ isOpen: boolean }>`
  position: fixed;
  top: 0;
  left: ${({ isOpen }) => (isOpen ? "0" : "-250px")}; 
  width: 250px;
  height: 100%;
  background-color: ${colorWW};
  border-right: 2px solid ${color1};
  border-radius: 3px;
  transition: left 0.3s ease;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const ToggleButton = styled.button<{ isOpen: boolean }>`
    position: absolute;
    top: 45%;
    right: -16px;
    z-index: 1100;
    padding: 5px;
    background-color: ${color3};
    color: #fff;
    height: 80px;
    width: 0.1px;
    border: none;
    border-radius: 5px;
    cursor: pointer;

    &:hover {
        background-color: ${color3};
    }
`;

export const StudyPlan = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
`

export const StudyTitle = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    font-size: 14px;
    font-weight: 300;
    margin-bottom: 20px;
    border-bottom: 1px solid ${colorG};
    height: 40px;
    width: 95%;
`

export const PlanBox = styled.div`
    margin-top: 9px;
    border: 2px solid ${color1};
    border-radius: 3px;
    background-color: ${color1};
    color: ${colorW};
    display: flex;
    justify-content: center;
    align-items: center;
    width: 200px;
    height: 40px;
`

export const StudySpace = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100%;
    overflow-y: scroll;
`
export const StudyBox = styled.div`
    height: 50%;
    width: 96%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`
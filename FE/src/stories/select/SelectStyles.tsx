import emotionStyled from "@emotion/styled";
import { color1, color2, colorW } from "../../constants/colors";
import { SelectProps } from "./SelectInterface";

const styled = emotionStyled;

const Container = styled.div<SelectProps>`
  position: relative;
  & > button {
    width: ${(props) => props.width};
    border: 3px solid ${color2};
    box-sizing: border-box;
    border-radius: 10px;
    padding: 12px 13px;
    font-family: sans-serif;
    font-style: normal;
    font-weight: 600;
    font-size: 14px;
    line-height: 16px;
    appearance: none;
    text-align: left;
    color: ${color2};
    background: ${colorW};
    transition: all 0.2s;
    cursor: pointer;
  }
  & > button:hover {
    color: ${color1};
    background: ${color2};
    box-sizing: border-box;
    border-radius: 10px;
    outline: none;
  }
  & > ul {
    display: none;
    position: absolute;
    margin: 0;
    padding: 0;
    width: ${(props) => props.width};
    max-height: ${(props) => props.maxHeight};
    list-style: none;
    background: ${colorW};
    border: 3px solid ${color2};
    box-sizing: border-box;
    box-shadow: 4px 4px 14px rgba(0, 0, 0, 0.15);
    border-radius: 10px;
    margin-top: 9px;
    overflow: scroll;
    scrollbar-width: none;
  }
  & > ul::-webkit-scrollbar {
    display: none;
  }
  & > ul > li {
    border: none;
    color: ${color1};
    background-color: ${colorW};
    font-family: "Roboto";
    font-style: normal;
    font-weight: 600;
    font-size: 14px;
    line-height: 16px;
    padding: 12px 17px;
    box-sizing: border-box;
    transition: all 0.2s;
    cursor: pointer;
  }
  & > ul > .selected {
    background: ${color1};
    color: ${color2};
  }
  & > ul > li:hover {
    color: ${color1};
    background: ${color2};
    box-sizing: border-box;
    text-align: left;
  }
`;

export { Container };

import emotionStyled from "@emotion/styled";
import { color1, color2, color3, color4, colorB } from "../../constants/colors";
import { InputProps } from "./InputInterface";

const styled = emotionStyled;

const Container = styled.div<InputProps>`
  font-family: sans-serif;
  font-size: 16px;
  width: ${(props) => props.width};
  position: relative;
  height: 50px;
  overflow: hidden;
  border-radius: ${(props) => props.format === "box" && "10px"};

  .input {
    width: 100%;
    height: 100%;
    color: ${colorB};
    border: none;
    outline: none;
    background: ${(props) => props.format === "box" && color3};
    transition: ${(props) => props.format === "box" && "all 0.3s ease"};
    padding-left: ${(props) => props.format === "box" && "10px"};
    padding-top: 10px;
  }

  .label {
    position: absolute;
    bottom: 0px;
    left: 0%;
    width: 100%;
    height: 100%;
    color: ${color1};
    pointer-events: none;
    border-bottom: ${(props) =>
      props.format === "line" && `1px solid ${color1}`};

    &::after {
      content: "";
      position: absolute;
      left: 0px;
      bottom: -1px;
      height: 100%;
      width: 100%;
      border-bottom: ${(props) =>
        props.format === "line" && `3px solid ${color2}`};
      transform: translateX(-100%);
      transition: transform 0.3s ease;
    }
  }

  .content {
    position: absolute;
    bottom: ${(props) => (props.format === "line" ? "5px" : "14px")};
    left: ${(props) => (props.format === "line" ? "0px" : "10px")};
    transition: all 0.3s ease;
  }

  .input:focus + .label .content,
  .input:valid + .label .content {
    transform: translateY(-${(props) => (props.format === "line" ? 150 : 80)}%);
    font-size: 14px;
    color: ${color2};
  }

  .input:focus + .label::after,
  .input:valid + .label::after {
    transform: translateX(0%);
  }

  .input:focus,
  .input:valid {
    background: ${(props) => props.format === "box" && color4};
    transition: ${(props) => props.format === "box" && "all 0.3s ease"};
  }
`;

export { Container };

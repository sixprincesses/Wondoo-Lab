import emotionStyled from "@emotion/styled";
import { color1, color2 } from "../../constants/colors";
import { ButtonProps } from "./ButtonInterface";

const styled = emotionStyled;

const StyledButton = styled.button<ButtonProps>`
  position: relative;
  font-family: sans-serif;
  font-size: ${(props) => {
    if (props.block) {
      return "16px";
    } else if (props.size === "small") {
      return "12px";
    } else if (props.size === "medium") {
      return "14px";
    } else if (props.size === "large") {
      return "16px";
    }
  }};
  display: ${(props) => (props.block ? "block" : "auto")};
  width: ${(props) => {
    if (props.block) {
      return "100%";
    } else if (props.size === "small") {
      return "60px";
    } else if (props.size === "medium") {
      return "80px";
    } else if (props.size === "large") {
      return "120px";
    }
  }};
  aspect-ratio: ${(props) => props.round === "circle" && "1/1"};
  padding: 0;
  line-height: ${(props) => {
    if (props.block) {
      return "50px";
    } else if (props.size === "small") {
      return "30px";
    } else if (props.size === "medium") {
      return "40px";
    } else if (props.size === "large") {
      return "50px";
    }
  }};
  border: ${(props) => {
    if (props.anime === "float") {
      return "none";
    } else if (props.format === "line") {
      return `3px solid ${color2}`;
    } else {
      return "none";
    }
  }};
  border-radius: ${(props) => {
    if (props.round === "block") {
      return "0";
    } else if (props.round === "none") {
      return "5px";
    } else if (props.round === "round") {
      return "100px";
    } else if (props.round === "circle") {
      return "50%";
    }
  }};
  font-weight: 600;
  color: ${color2};
  background: ${(props) => {
    if (props.anime === "float") {
      return "none";
    } else if (props.format === "line") {
      return "white";
    } else {
      return `${color1}`;
    }
  }};
  box-shadow: ${(props) => {
    if (props.anime === "push") {
      return `0px 5px 0px 0px ${color2}`;
    } else if (props.anime === "float") {
      return "0px 0px 0px 0px rgba(0, 0, 0, 0.5)";
    } else {
      return "none";
    }
  }};
  transition: all 0.2s;
  cursor: pointer;

  &:hover {
    ${(props) => {
      if (props.anime === "fade") {
        return "color:" + color1 + ";background:" + color2 + ";";
      } else if (props.anime === "push") {
        return (
          "margin-top: 9px;" +
          "margin-bottom: 3px;" +
          `box-shadow: 0px 2px 0px 0px ${color2};`
        );
      }
    }}
  }

  &:active {
    ${(props) => {
      if (props.anime === "push") {
        return (
          "margin-top: 15px;" +
          "margin-bottom: 5px;" +
          `box-shadow: 0px 0px 0px 0px ${color2};`
        );
      }
    }}
  }

  &:before {
    ${(props) => {
      if (props.anime === "float") {
        return (
          `content: "${props.label}";` +
          "display: flex;" +
          "align-items: center;" +
          "justify-content: center;" +
          "position: absolute;" +
          "top: 0;" +
          "left: 0;" +
          "width: 100%;" +
          "height: 100%;" +
          `color: ${color1};` +
          `background: ${color2};` +
          "box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0.4);" +
          "transition: all 0.2s;"
        );
      }
    }}
    border-radius: ${(props) => {
      if (props.anime === "float") {
        if (props.round === "block") {
          return "0";
        } else if (props.round === "none") {
          return "5px";
        } else if (props.round === "round") {
          return "100px";
        } else if (props.round === "circle") {
          return "50%";
        }
      }
    }};
  }

  &:hover:before {
    ${(props) => {
      if (props.anime === "float") {
        return (
          "margin-top: -2px;" +
          "margin-left: 0px;" +
          "transform: scale(1.1, 1.1);" +
          "-ms-transform: scale(1.1, 1.1);" +
          "-webkit-transform: scale(1.1, 1.1);" +
          "box-shadow: 0px 5px 5px -2px rgba(0, 0, 0, 0.25);"
        );
      }
    }}
  }
`;

export { StyledButton };

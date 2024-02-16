import { color1 } from "../../constants/colors";
import { styled } from "../../constants/styled";
import { ToggleButtonProps } from "../../interfaces/ToggleButtonProps";

const DivToggleButtonContainer = styled.div<ToggleButtonProps>`
  margin: auto 30px;
  position: relative;
  border-radius: 20px;
  cursor: pointer;

  width: ${(props) => props.width};
  height: ${(props) => props.height};

  background-color: rgb(233, 233, 234);
  transition: 0.5s;

  &.toggle-on {
    background-color: ${color1};
  }
`;

const DivToggleButtonCircle = styled.div`
  position: absolute;
  top: 10%;
  left: 5%;

  border: none;
  border-radius: 100%;

  height: 80%;
  aspect-ratio: 1/1;

  background-color: rgb(255, 254, 255);

  &.toggle-on {
    left: 52.5%;
  }
  transition: 0.3s;
`;

export { DivToggleButtonCircle, DivToggleButtonContainer };

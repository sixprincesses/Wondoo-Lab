import { useEffect } from "react";
import {
  DivToggleButtonCircle,
  DivToggleButtonContainer,
} from "./ToggleButtonStyle";
import { ToggleButtonProps } from "../../interfaces/ToggleButtonProps";

const ToggleButton = ({
  width,
  height,
  isToggleOn,
  handleClick,
}: ToggleButtonProps) => {
  useEffect(() => {
    console.log(isToggleOn);
  }, [isToggleOn]);

  return (
    <DivToggleButtonContainer
      className={isToggleOn ? "toggle-on" : undefined}
      onClick={handleClick}
      width={width}
      height={height}
    >
      <DivToggleButtonCircle className={isToggleOn ? "toggle-on" : undefined} />
    </DivToggleButtonContainer>
  );
};

export default ToggleButton;

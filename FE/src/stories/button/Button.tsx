import { ButtonProps } from "./ButtonInterface";
import { StyledButton } from "./ButtonStyle";

export const Button = ({
  format = "line",
  size = "medium",
  round = "none",
  anime = "none",
  label = "push",
  block = false,
  disabled = false,
}: ButtonProps) => {
  return (
    <StyledButton
      format={format}
      block={block}
      size={size}
      round={round}
      anime={anime}
      label={label}
      disabled={disabled}
    >
      {label}
    </StyledButton>
  );
};

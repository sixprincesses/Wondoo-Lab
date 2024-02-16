interface ButtonProps {
  format?: "line" | "box";
  size?: "small" | "medium" | "large";
  round?: "block" | "none" | "round" | "circle";
  anime?: "none" | "fade" | "float" | "push";
  label: string;
  block?: boolean;
  disabled?: boolean;
  type?: string;
}

export type { ButtonProps };

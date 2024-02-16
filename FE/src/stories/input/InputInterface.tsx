import React from "react";

interface InputProps {
  format?: "line" | "box";
  label?: string;
  width?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  value?: string;
}

export type { InputProps };

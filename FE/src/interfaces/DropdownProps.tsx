import { ReactNode } from "react";

interface DropdownBtn {
  useFunction: () => void;
  content: string;
}

interface DropdownState {
  position: {
    top?: string | null;
    right?: string | null;
    bottom?: string | null;
    left?: string | null;
  };
  size: {
    width: number;
    height: number;
  };
  buttons: DropdownBtn[];
  isActive: boolean;
}

interface DropdownProps {
  state: DropdownState;
  setState: React.Dispatch<any>;
  children: ReactNode;
}

export type { DropdownBtn, DropdownProps, DropdownState };

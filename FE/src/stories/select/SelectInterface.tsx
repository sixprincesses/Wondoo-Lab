import React from "react";

interface OptionProps {
  className?: string;
  key?: string | number;
  value: string;
  children: string;
}

interface SelectProps {
  width?: string;
  maxHeight?: string;
  className?: string;
  title?: OptionProps;
  defaultOption?: OptionProps;
  options?: OptionProps[];
  prop?: any;
  setProp?: React.Dispatch<React.SetStateAction<any>>;
}

export type { OptionProps, SelectProps };

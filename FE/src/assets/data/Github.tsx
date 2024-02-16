import { Dispatch } from "react";

interface dummyOptions {
  value: string;
  children: string;
}

interface dummySelect {
  selected: dummyOptions;
  options: dummyOptions[];
}

interface dummySelectProps {
  state: dummySelect;
  setSelected: Dispatch<dummySelect>;
}

export type { dummyOptions, dummySelect, dummySelectProps };

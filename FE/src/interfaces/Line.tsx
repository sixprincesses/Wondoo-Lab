type line = [number | string, number | string, string, string, string, number];

interface lineInfo {
  lineNo: number;
  value: (string | number)[];
}

export type { line, lineInfo };

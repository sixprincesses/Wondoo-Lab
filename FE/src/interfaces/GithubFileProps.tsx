import { Dispatch, SetStateAction } from "react";
import { lineInfo } from "./Line";
import { File } from "./tempFeed/File";

interface GithubFileProps {
  file: File;
  id: number;
  data: Map<string, lineInfo[]>;
  setData: Dispatch<SetStateAction<Map<string, lineInfo[]>>>;
}

export type { GithubFileProps };

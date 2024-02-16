import { ChangeEvent, useState } from "react";
import angleBracket from "../../assets/icon/angleBracket.png";
import { GithubFileProps } from "../../interfaces/GithubFileProps";
import { line, lineInfo } from "../../interfaces/Line";
import {
  CheckBox,
  FileContent,
  FileHeader,
  FileRollup,
  FileTitle,
  Line,
  Wrapper,
} from "./GithubFileStyle";

const GithubFile = ({ id, file, data, setData }: GithubFileProps) => {
  // 파일 데이터 관리 로직
  let addno: number = 0;
  let subno: number = 0;
  let lineno: number = 0;
  const lines: string[] = file?.patch?.split("\n");
  const divides: line[] = lines?.map((line) => {
    // 파일 로우 데이터 파싱
    const lineInfo = /^@@\s-(\d+),(\d+)\s\+(\d+),(\d+)\s@@.*$/;
    const addLine = /^\+(.*)/;
    const subLine = /^-(.*)/;

    const matchInfo = line.match(lineInfo);
    const matchAdd = line.match(addLine);
    const matchSub = line.match(subLine);

    if (matchInfo) {
      addno = parseInt(matchInfo[1]);
      subno = parseInt(matchInfo[3]);
      return [
        "...",
        "...",
        " ",
        line.replaceAll(" ", "\u2003"),
        file.filename,
        lineno++,
      ];
    } else if (matchAdd) {
      return [
        "",
        addno++,
        "+",
        matchAdd[1].replaceAll(" ", "\u2003"),
        file.filename,
        lineno++,
      ];
    } else if (matchSub) {
      return [
        subno++,
        "",
        "-",
        matchSub[1].replaceAll(" ", "\u2003"),
        file.filename,
        lineno++,
      ];
    } else {
      return [
        subno++,
        addno++,
        "",
        line.replaceAll(" ", "\u2003"),
        file.filename,
        lineno++,
      ];
    }
  });
  const handleLineClass = (line: string): string => {
    // 구분자를 통한 클래스 부여
    switch (line) {
      case " ":
        return "line-info";
      case "+":
        return "add";
      case "-":
        return "sub";
      default:
        return "none";
    }
  };
  const handleCheck = (e: ChangeEvent<HTMLInputElement>, value: line) => {
    const isChecked: boolean = e.target.checked;
    const targetData: lineInfo[] | undefined = data.get(value[4]);
    if (isChecked) {
      // 체크박스에 체크되었을 때, 1. 있는 값이라면, 2. 없는 값이라면
      if (targetData) {
        targetData.push({ lineNo: value[5], value: value.slice(0, 4) });
      } else {
        data.set(value[4], [{ lineNo: value[5], value: value.slice(0, 4) }]);
      }
      setData(new Map(data));
    } else {
      // 체크 해제 되었을 때, 1. 값이 비지 않았다면, 2. 값이 비었다면
      if (targetData) {
        const filtered = targetData.filter(
          (line) => !(line.lineNo === value[5])
        );
        if (filtered.length) {
          data.set(value[4], filtered);
        } else {
          data.delete(value[4]);
        }
        setData(new Map(data));
      }
    }
  };

  // 롤업 로직
  const [isRolled, setIsRolled] = useState(false);
  const handleRollup = () => {
    setIsRolled(!isRolled);
  };

  return (
    <Wrapper>
      <FileHeader>
        <FileRollup onClick={handleRollup} isRolled={isRolled}>
          <img src={angleBracket} alt="펼치기" />
        </FileRollup>
        <FileTitle>{file.filename}</FileTitle>
      </FileHeader>
      <FileContent isRolled={isRolled}>
        {divides?.map((line) => (
          <Line
            key={JSON.stringify({ id, line })}
            className={handleLineClass(line[2])}
          >
            <CheckBox>
              <input
                type="checkbox"
                id={JSON.stringify({ id, line })}
                onChange={(e) => handleCheck(e, line)}
              />
              <label htmlFor={JSON.stringify({ id, line })}></label>
            </CheckBox>
            {line?.slice(0, 4)?.map((part, idx) => (
              <p key={idx}>{part}</p>
            ))}
          </Line>
        ))}
      </FileContent>
    </Wrapper>
  );
};

export default GithubFile;

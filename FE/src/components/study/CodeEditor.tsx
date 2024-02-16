import "ace-builds/src-noconflict/ace";
import "ace-builds/src-noconflict/ext-language_tools";
import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/mode-css";
import "ace-builds/src-noconflict/mode-html";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/mode-javascript";
import "ace-builds/src-noconflict/mode-json";
import "ace-builds/src-noconflict/mode-python";
import "ace-builds/src-noconflict/mode-sql";
import "ace-builds/src-noconflict/mode-typescript";
import "ace-builds/src-noconflict/snippets/c_cpp";
import "ace-builds/src-noconflict/snippets/css";
import "ace-builds/src-noconflict/snippets/html";
import "ace-builds/src-noconflict/snippets/java";
import "ace-builds/src-noconflict/snippets/javascript";
import "ace-builds/src-noconflict/snippets/json";
import "ace-builds/src-noconflict/snippets/python";
import "ace-builds/src-noconflict/snippets/sql";
import "ace-builds/src-noconflict/snippets/typescript";
import "ace-builds/src-noconflict/theme-github";
import { MouseEvent, useEffect, useRef, useState } from "react";
import AceEditor from "react-ace";
import { dummySelect } from "../../assets/data/Github";
import angleBracket from "../../assets/icon/angleBracket.png";
import closeIcon from "../../assets/icon/closeIcon.png";
import drag from "../../assets/icon/drag.png";
import { useAppDispatch } from "../../store/hooks";
import { setInstanceData } from "../../store/tempFeedSlice";
import {
  Background,
  LangSelector,
  Main,
  Option,
  OptionBox,
  Select,
  Selected,
} from "./CodeEditorStyle";
import {
  CloseBtn,
  DragBtn,
  Header,
  Rollup,
  Title,
  Wrapper,
} from "./GithubStyle";

interface CodeEditorProps {
  id: number;
  deleteData: (id: number) => void;
  initialData?: {
    language: string;
    content: string;
  };
}

const CodeEditor = ({ id, deleteData, initialData }: CodeEditorProps) => {
  const dispatch = useAppDispatch();

  // 언어 선택 로직
  const selectorRef = useRef<HTMLDivElement | null>(null);
  const [lang, setLang] = useState<dummySelect>({
    selected: { value: "json", children: "언어 선택" },
    options: [
      { value: "c_cpp", children: "c++" },
      { value: "python", children: "python" },
      { value: "java", children: "java" },
      { value: "javascript", children: "javascript" },
      { value: "typescript", children: "typescript" },
      { value: "html", children: "html" },
      { value: "json", children: "json" },
      { value: "css", children: "css" },
      { value: "sql", children: "sql" },
    ],
  });
  const [isDisplay, setIsDisplay] = useState(false);
  const [isDown, setIsDown] = useState(true);
  const openDisplay = (e: MouseEvent) => {
    const target = e.target as HTMLDivElement;
    const parentElementStyle = target.parentElement?.style;
    const pos = selectorRef.current?.getBoundingClientRect().y;
    const viewportHeight = window.innerHeight;
    if (typeof pos === "number" && pos > viewportHeight / 2) {
      setIsDown(false);
    } else {
      setIsDown(true);
    }
    setIsDisplay(true);
    if (parentElementStyle) {
      parentElementStyle.zIndex = "2";
    }
  };
  const closeDisplay = (e: MouseEvent) => {
    const target = e.target as HTMLDivElement;
    const parentElementStyle = target.parentElement?.style;
    setIsDisplay(false);
    if (parentElementStyle) {
      parentElementStyle.zIndex = "0";
    }
  };
  const handleSelected = (e: MouseEvent) => {
    const target = e.target as HTMLDivElement;
    const grandParentElementStyle = target.parentElement?.parentElement?.style;
    if (!target.dataset.value) return;
    setLang({
      ...lang,
      selected: {
        value: target.dataset.value,
        children: target.innerText,
      },
    });
    setIsDisplay(false);
    if (grandParentElementStyle) {
      grandParentElementStyle.zIndex = "0";
    }
  };

  // 내용 로직
  const [code, setCode] = useState("");
  const onChange = (newValue: string) => {
    setCode(newValue);
  };

  // 롤업 로직
  const [isRolled, setIsRolled] = useState(false);
  const handleRollup = () => {
    setIsRolled(!isRolled);
  };

  // 데이터 저장 로직
  useEffect(() => {
    const codeEditor = "```".concat(lang.selected.value, "\n", code, "\n```");
    dispatch(setInstanceData({ id, data: ["codeEditor", codeEditor] }));
  }, [code, lang.selected.value]);

  // 데이터 불러오기 로직
  useEffect(() => {
    if (!initialData) return;
    setLang({
      ...lang,
      selected: {
        value: initialData.language,
        children: initialData.language,
      },
    });
    setCode(initialData.content);
  }, []);

  return (
    <Wrapper>
      <Header>
        <Rollup onClick={handleRollup} isRolled={isRolled}>
          <img src={angleBracket} alt="펼치기" />
        </Rollup>
        <Title>Code Editor</Title>
        <DragBtn>
          <img src={drag} alt="드래그" />
        </DragBtn>
        <CloseBtn onClick={() => deleteData(id)}>
          <img src={closeIcon} alt="닫기" />
        </CloseBtn>
      </Header>
      <Main isRolled={isRolled}>
        <LangSelector>
          <Select ref={selectorRef}>
            <Selected onClick={openDisplay}>{lang.selected.children}</Selected>
            <OptionBox isDisplay={isDisplay} isDown={isDown}>
              {lang.options.map((option, idx) => (
                <Option
                  key={idx}
                  data-value={option.value}
                  onClick={handleSelected}
                >
                  {option.children}
                </Option>
              ))}
            </OptionBox>
            <Background
              onClick={closeDisplay}
              isDisplay={isDisplay}
            ></Background>
          </Select>
        </LangSelector>
        <AceEditor
          value={code}
          onChange={onChange}
          placeholder={"코드를 입력해주세요"}
          mode={lang.selected.value}
          theme="github"
          width="100%"
          minLines={3}
          fontSize={16}
          tabSize={2}
          editorProps={{ $blockScrolling: true }}
          setOptions={{ useWorker: false }}
          enableBasicAutocompletion={true}
          enableLiveAutocompletion={true}
          enableSnippets={true}
          maxLines={Infinity}
        />
      </Main>
    </Wrapper>
  );
};

export default CodeEditor;

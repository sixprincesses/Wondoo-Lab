import { Editor } from "@toast-ui/react-editor";
import { useEffect, useRef, useState } from "react";
import angleBracket from "../../assets/icon/angleBracket.png";
import closeIcon from "../../assets/icon/closeIcon.png";
import drag from "../../assets/icon/drag.png";
import { colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";
import { useAppDispatch } from "../../store/hooks";
import { setInstanceData } from "../../store/tempFeedSlice";
import {
  CloseBtn,
  DragBtn,
  Header,
  Rollup,
  Title,
  Wrapper,
} from "./GithubStyle";

const Main = styled.div<{ isRolled: boolean }>`
  z-index: 1;
  position: relative;
  width: 100%;
  display: ${(props) => (props.isRolled ? "none" : "block")};
  border-top: 1px solid ${colorG};
  border-radius: 0 0 5px 5px;
  background-color: ${colorWW};
  overflow: hidden;
  & .toastui-editor-tabs {
    display: none;
  }
`;

interface MarkdownProps {
  id: number;
  deleteData: (id: number) => void;
  initialData?: string;
}

const Markdown = ({ id, deleteData, initialData }: MarkdownProps) => {
  const dispatch = useAppDispatch();

  // 마크다운 조작 로직
  const editorRef = useRef<Editor>(null);
  const [content, setContent] = useState("");
  const handleContent = () => {
    const content: string = editorRef.current?.getInstance().getMarkdown();
    setContent(content);
  };

  // 롤업 로직
  const [isRolled, setIsRolled] = useState(false);
  const handleRollup = () => {
    setIsRolled(!isRolled);
  };

  // 데이터 저장 로직
  useEffect(() => {
    const markdown: string = editorRef.current?.getInstance().getMarkdown();
    dispatch(setInstanceData({ id, data: ["markdown", markdown] }));
  }, [content]);

  // 데이터 불러오기 로직
  useEffect(() => {
    if (typeof initialData === "undefined") return;
    editorRef.current?.getInstance().setMarkdown(initialData);
    setContent(initialData);
  }, []);

  return (
    <Wrapper>
      <Header>
        <Rollup onClick={handleRollup} isRolled={isRolled}>
          <img src={angleBracket} alt="펼치기" />
        </Rollup>
        <Title>Markdown</Title>
        <DragBtn>
          <img src={drag} alt="드래그" />
        </DragBtn>
        <CloseBtn onClick={() => deleteData(id)}>
          <img src={closeIcon} alt="닫기" />
        </CloseBtn>
      </Header>
      <Main isRolled={isRolled}>
        {editorRef && (
          <Editor
            ref={editorRef}
            initialValue={content}
            height="auto"
            minHeight="auto"
            initialEditType="markdown"
            placeholder="텍스트를 입력해주세요"
            previewStyle="tab"
            hideModeSwitch={true}
            onChange={handleContent}
          />
        )}
      </Main>
    </Wrapper>
  );
};

export default Markdown;

import "@toast-ui/editor/dist/theme/toastui-editor-dark.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/react-editor";
import { useRef } from "react";

interface EditorProps {
  /**
   * 미리보기 스타일
   */
  previewStyle?: "vertical" | "tab";
  /**
   * 에디터 초기값
   */
  initialValue: string;
}

const InstanceMd = ({ previewStyle, initialValue }: EditorProps) => {
  const editorRef = useRef<Editor>(null);

  // 빌드 에러용
  console.log(previewStyle, initialValue);

  return (
    <div className="MarkDownEditor" style={{ width: "100%" }}>
      <Editor
        ref={editorRef}
        height="200px"
        // placeholder="여기에 입력 해주세요."
        // previewStyle={previewStyle}
        initialEditType="markdown"
        hideModeSwitch={true}
        usageStatistics={false}
        initialValue=" "
      />
    </div>
  );
};

export default InstanceMd;

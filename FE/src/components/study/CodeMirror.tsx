import { useState } from 'react';
import AceEditor from 'react-ace';
import 'ace-builds/src-noconflict/mode-python';
// import 'ace-builds/src-noconflict/theme-monokai';
import styled from '@emotion/styled';

interface CodeMirrorProps {
  className?: string;
}

const Container = styled.div`
  width: 100%;
  border: 1px solid black;
  padding: 2px;
  border-radius: 10px;
  margin-bottom: 4px;
`;

const CodeMirror = ({ className } : CodeMirrorProps) => {
  const [code, setCode] = useState('');


  const onChange = (newValue: string) => {
    setCode(newValue);
  };

  return (
    <Container className={className}>
      <AceEditor
        className="problem-code-input"
        placeholder={`code here! python`}
        mode="python"
        // theme="monokai" // 이 테마 적용하면 어두워짐
        name="codeInput"
        width="100%"
        height="80px"
        onChange={onChange}
        fontSize={18}
        editorProps={{ $blockScrolling: true }}
        showPrintMargin={true}
        showGutter={true}
        highlightActiveLine={true}
        value={code}
        maxLines={Infinity}
      />
    </Container>
  );
};

export default CodeMirror;

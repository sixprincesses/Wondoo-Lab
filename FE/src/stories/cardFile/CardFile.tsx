import * as Icons from "@storybook/icons";
import { useState } from "react";
import { CardFileProps } from "./CardFileInterface";
import { Container, Header, Line } from "./CardFileStyles";

const CardFile = ({
  filename = "src/App.js",
  changes = 53,
  patch = '@@ -1,37 +1,30 @@\n-import { useState } from "react";\n+import { useEffect, useState } from "react";\n \n function App() {\n-  const [toDo, setToDo] = useState("");\n-  const [toDos, setToDos] = useState([]);\n-  const onChange = (event) => setToDo(event.target.value);\n-  const onSubmit = (event) => {\n-    event.preventDefault();\n-    if (toDo === "") {\n-      return;\n-    }\n-    setToDos((currentArray) => [toDo, ...currentArray]);\n-    setToDo("");\n-  };\n-  console.log(toDos);\n-  console.log(toDos.map((item, index) => <li key={index}>{item}</li>));\n+  const [loading, setLoading] = useState(true);\n+  const [coins, setCoins] = useState([]);\n+  useEffect(() => {\n+    fetch("https://api.coinpaprika.com/v1/tickers")\n+      .then((response) => response.json())\n+      .then((json) => {\n+        setCoins(json);\n+        setLoading(false);\n+      });\n+  }, []);\n   return (\n     <div>\n-      <h1>My To Dos ({toDos.length})</h1>\n-      <form onSubmit={onSubmit}>\n-        <input\n-          onChange={onChange}\n-          value={toDo}\n-          type="text"\n-          placeholder="Write your to do..."\n-        />\n-        <button>Add To Do</button>\n-      </form>\n-      <hr />\n-      <ul>\n-        {toDos.map((item, index) => (\n-          <li key={index}>{item}</li>\n-        ))}\n-      </ul>\n+      <h1>The Coins! {loading ? "" : `(${coins.length})`}</h1>\n+      {loading ? (\n+        <strong>Loading...</strong>\n+      ) : (\n+        <select>\n+          {coins.map((coin) => (\n+            <option>\n+              {coin.name} ({coin.symbol}): ${coin.quotes.USD.price} USD\n+            </option>\n+          ))}\n+        </select>\n+      )}\n     </div>\n   );\n }',
}: CardFileProps) => {
  let addno = 0;
  let subno = 0;
  const lines = patch.split("\n");
  const divides: [string, number | string, number | string, string][] =
    lines.map((line) => {
      // 정규표현식
      const lineInfo = /^@@\s-(\d+),(\d+)\s\+(\d+),(\d+)\s@@.*$/;
      const addLine = /^\+(.*)/;
      const subLine = /^-(.*)/;

      const matchInfo = line.match(lineInfo);
      const matchAdd = line.match(addLine);
      const matchSub = line.match(subLine);
      if (matchInfo) {
        addno = parseInt(matchInfo[1]);
        subno = parseInt(matchInfo[3]);
        return [" ", "...", "...", line.replaceAll(" ", "\u2003")];
      } else if (matchAdd) {
        return ["+", "", addno++, matchAdd[1].replaceAll(" ", "\u2003")];
      } else if (matchSub) {
        return ["-", subno++, "", matchSub[1].replaceAll(" ", "\u2003")];
      } else {
        return ["", subno++, addno++, line.replaceAll(" ", "\u2003")];
      }
    });
  const [toggle, setToggle] = useState(true);

  const handleLineClass = (line: string) => {
    switch (line) {
      case " ":
        return "line-info";
      case "+":
        return "add";
      case "-":
        return "sub";
      case "":
        return "none";
    }
  };
  const handleToggle = () => {
    setToggle(!toggle);
  };

  return (
    <Container>
      <Header toggle={toggle}>
        <div className="file-info">
          <button onClick={handleToggle}>
            {toggle ? <Icons.ChevronDownIcon /> : <Icons.ChevronRightIcon />}
          </button>
          <div className="changes">
            <span>{changes}</span>
          </div>
          <div className="file-name">
            <p>{filename}</p>
          </div>
          <button>
            <Icons.SwitchAltIcon />
          </button>
        </div>
        <div className="file-action">
          <button>
            <Icons.MarkupIcon />
          </button>
          <button>
            <Icons.DocumentIcon />
          </button>
        </div>
      </Header>
      {toggle &&
        divides.map((line) => (
          <Line className={handleLineClass(line[0])}>
            <p>{line[1]}</p>
            <p>{line[2]}</p>
            <p>{line[0]}</p>
            <p>{line[3]}</p>
          </Line>
        ))}
    </Container>
  );
};

export { CardFile };

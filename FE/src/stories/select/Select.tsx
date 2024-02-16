import React, { useRef } from "react";
import { SelectProps } from "./SelectInterface";
import { Container } from "./SelectStyles";

const Select = ({
  className = "",
  width = "200px",
  maxHeight = "188px",
  defaultOption,
  options = [
    { value: "one", children: "one" },
    { value: "two", children: "two" },
    { value: "three", children: "three" },
    { value: "four", children: "four" },
    { value: "five", children: "five" },
    { value: "six", children: "six" },
  ],
  prop = { title: { value: "title", children: "선택하세요!" } },
  setProp,
}: SelectProps) => {
  const ul = useRef<HTMLUListElement>(null);

  const handleUlDisplay = () => {
    if (ul.current?.style.display !== "none") {
      ul.current?.style.setProperty("display", "none");
    } else {
      ul.current?.style.setProperty("display", "block");
    }
  };
  const handleValue = (e: React.MouseEvent<HTMLLIElement>) => {
    const target = e.target as HTMLLIElement;
    if (target.dataset.option) {
      const option = JSON.parse(target.dataset?.option);
      if (typeof setProp === "function") {
        setProp({ ...prop, title: option });
      }
      handleUlDisplay();
      if (ul.current) {
        for (let i = 0; i < ul.current.children.length; i++) {
          const child = ul.current.children[i];
          child.classList.remove("selected");
        }
      }
      if (option !== prop.name) {
        target.classList.add("selected");
      }
    } else {
      console.error("value가 지정되지 않았습니다.");
    }
  };

  return (
    <Container className={className} width={width} maxHeight={maxHeight}>
      <button onClick={handleUlDisplay}>{prop.title?.children}</button>
      <ul ref={ul}>
        {defaultOption?.children && (
          <li onClick={handleValue} data-option={JSON.stringify(defaultOption)}>
            {defaultOption?.children}
          </li>
        )}
        {options?.map((option, idx) => (
          <li
            onClick={handleValue}
            key={idx}
            data-option={JSON.stringify(option)}
          >
            {option.children}
          </li>
        ))}
      </ul>
    </Container>
  );
};

export { Select };

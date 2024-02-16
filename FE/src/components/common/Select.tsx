import { MouseEvent, useRef, useState } from "react";
import { dummySelectProps } from "../../assets/data/Github";
import {
  Background,
  Option,
  OptionBox,
  Selected,
  Wrapper,
} from "./SelectStyle";

const Select = ({ state, setSelected }: dummySelectProps) => {
  const { selected, options } = state;
  const selectorRef = useRef<HTMLDivElement | null>(null);
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
    const grandparentElementStyle = target.parentElement?.parentElement?.style;
    if (!target.dataset.value) return;
    setSelected({
      ...state,
      selected: {
        value: target.dataset.value,
        children: target.innerText,
      },
    });
    setIsDisplay(false);
    if (grandparentElementStyle) {
      grandparentElementStyle.zIndex = "2";
    }
  };

  return (
    <Wrapper ref={selectorRef}>
      <Selected onClick={openDisplay}>{selected.children}</Selected>
      <OptionBox isDisplay={isDisplay} isDown={isDown}>
        {options.map((option, idx) => (
          <Option key={idx} data-value={option.value} onClick={handleSelected}>
            {option.children}
          </Option>
        ))}
      </OptionBox>
      <Background onClick={closeDisplay} isDisplay={isDisplay}></Background>
    </Wrapper>
  );
};

export default Select;

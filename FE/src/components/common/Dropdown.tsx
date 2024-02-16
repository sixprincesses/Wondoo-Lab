import { createRef } from "react";
import { DropdownProps } from "../../interfaces/DropdownProps";
import { Background, DropdownMenus, Wrapper } from "./DropdownStyle";

const Dropdown = ({ state, setState, children }: DropdownProps) => {
  const { position, size, buttons, isActive } = state;
  const { width, height } = size;
  const lineHeight = Math.floor(height / buttons.length);
  const childNum = buttons.length;

  const menuBtn = createRef<HTMLDivElement>();
  const menu = createRef<HTMLDivElement>();
  const handleDropdown = () => {
    setState({ ...state, isActive: !state.isActive });
  };
  const closeDropdown = () => {
    setState({ ...state, isActive: false });
  };
  const clickButton = (func: () => void) => {
    func();
    setState({ ...state, isActive: false });
  };

  return (
    <Wrapper>
      <Background isActive={isActive} onClick={closeDropdown}></Background>
      <div ref={menuBtn} onClick={handleDropdown}>
        {children}
      </div>
      <DropdownMenus
        ref={menu}
        position={position}
        width={width}
        height={height}
        lineHeight={lineHeight}
        childNum={childNum}
        isActive={isActive}
      >
        {state.buttons?.map((btn, idx) => (
          <button key={idx} onClick={() => clickButton(btn.useFunction)}>
            {btn.content}
          </button>
        ))}
      </DropdownMenus>
    </Wrapper>
  );
};

export default Dropdown;

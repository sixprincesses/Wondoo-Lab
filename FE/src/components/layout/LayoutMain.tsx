import { useRef } from "react";
import { Outlet } from "react-router-dom";
import { styled } from "../../constants/styled";
import { ReactNodeProps } from "../../interfaces/ReactNodeProps";
import ScrollToTop from "../common/ScrollToTop";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr 630px 300px 1fr;
  gap: 20px;
  overflow-y: scroll;
  padding-bottom: 200px;
  & > :nth-of-type(1) {
    grid-column: 2;
  }
  & > :nth-of-type(2) {
    grid-column: 3;
  }
`;

const LayoutMain = ({ children }: ReactNodeProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const callbacck = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = 0;
    }
  };

  return (
    <>
      <Wrapper ref={scrollRef}>
        <Outlet />
        {children}
      </Wrapper>
      <ScrollToTop callback={callbacck} />
    </>
  );
};

export default LayoutMain;

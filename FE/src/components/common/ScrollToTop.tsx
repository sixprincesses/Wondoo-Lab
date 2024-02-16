import ScrollToTopIcon from "../../assets/icon/ScrollToTopIcon.png";
import { styled } from "../../constants/styled";

const Btn = styled.div`
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 50%;
  background: transparent;
  cursor: pointer;
  &:hover {
    opacity: 0.7;
  }
`;

const Icon = styled.img`
  width: 20px;
  height: 20px;
  object-fit: cover;
  rotate: 270deg;
`;

interface ScrollToTopProps {
  callback: (params?: any) => any;
}

const ScrollToTop = ({ callback }: ScrollToTopProps) => {
  return (
    <Btn onClick={callback}>
      <Icon src={ScrollToTopIcon} alt="위로" />
    </Btn>
  );
};

export default ScrollToTop;

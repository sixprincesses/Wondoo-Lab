import { color1, colorG, colorNG, colorW, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  display: flex;
  border: 2px solid ${colorG};
  border-bottom: none;
  border-radius: 10px 10px 0 0;
  background-color: ${colorWW};
  padding: 20px 20px 0 20px;
  & > div:nth-of-type(1) {
    flex: 1;
    display: flex;
    align-items: center;
    gap: 20px;
    padding-bottom: 20px;
    border-bottom: 3px solid ${colorG};
    & > img:nth-of-type(1) {
      width: 50px;
      height: 50px;
      object-fit: cover;
      border-radius: 50%;
    }
  }
`;

const Form = styled.form`
  flex: 1;
  height: 100%;
  display: flex;
  align-items: center;
  gap: 20px;
`;

const Input = styled.div`
  flex: 1;
  position: relative;
  height: 100%;
  overflow: hidden;

  .input {
    flex: 1;
    width: 100%;
    height: 100%;
    color: ${colorNG};
    background-color: ${colorNG};
    border-radius: 5px;
    border: none;
    padding-left: 10px;
    outline: none;

    &:focus + .label::after,
    &:not(:placeholder-shown) + .label::after {
      transform: translateX(0%);
    }
  }

  .label {
    position: absolute;
    bottom: 0px;
    left: 0%;
    height: 100%;
    width: 100%;
    pointer-events: none;

    &::after {
      content: "";
      position: absolute;
      left: 0px;
      bottom: -1px;
      height: 100%;
      width: 100%;
      border-bottom: 3px solid ${colorG};
      transform: translateX(-100%);
      transition: transform 0.5s ease;
    }
  }
`;

const Button = styled.button`
  width: 70px;
  padding: 0;
  line-height: 35px;
  border-radius: 10px;
  border: none;
  background: ${color1};
  color: ${colorW};
  cursor: pointer;
  &:hover {
    opacity: 0.7;
  }
`;

export { Button, Form, Input, Wrapper };

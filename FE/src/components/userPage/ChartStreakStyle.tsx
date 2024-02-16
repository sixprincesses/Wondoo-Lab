import { styled } from "../../constants/styled";

const DivStreakContainer = styled.div`
  margin: auto;
  width: 100%;
`;

const DivStreakCountStyled = styled.div`
  margin-top: 20px 40px;

  h2 {
    margin: 20px 40px;
  }
`;

const SvgStreakStyled = styled.svg<{ width: string }>`
  width: ${(props) => props.width};
  min-height: 180px;

  rect:hover {
    stroke-width: 5;
  }

  text {
    margin-right: 8px;
  }
`;

export { DivStreakContainer, DivStreakCountStyled, SvgStreakStyled };

import { colorB, colorG, colorWW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const Wrapper = styled.div`
  height: 100%;
  grid-column: 2/3;
  overflow-y: scroll;
  margin-top: 15px;
`;

const FollowBox = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  &:hover {
    cursor: pointer;
  }
`;

const FollowMember = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 15px;
  padding: 4px 10px;
  border: 1px solid ${colorG};
  border-radius: 4px;
  box-shadow: 2px ${colorB};
  background-color: ${colorWW};

  & img {
    border-radius: 50%;
    overflow: hidden;
    object-fit: cover;
    margin-right: 6px;
  }
`;

export { FollowBox, FollowMember, Wrapper };

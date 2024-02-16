import styled from "@emotion/styled";
import { css, keyframes } from "@emotion/react";
import { color1, colorG, colorWW, colorW, color3 } from "../../constants/colors";

const rollDown = keyframes`
  0% {
    max-height: 0;
  }
  1% {
    max-height: 0;
  }
  100% {
    max-height: 550px;
  }
`;

const rollUp = keyframes`
  0% {
    max-height: 550px;
  }
  99% {
    max-height: 0;
  }
  100% {
    max-height: 0;
  }
`;

export const MessagesWrapper = styled.div<{ showMessages: boolean }>`
  position: absolute;
  top: 60px;
  right: 20px;
  width: 400px;
  max-height: 660px;
  background-color: ${colorWW};
  border: 1px solid ${colorG};
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);  border-radius: 8px;
  animation: ${({ showMessages }) =>
    showMessages
      ? css`${rollDown} 0.5s`
      : css`${rollUp} 0.5s`
  };
`;

export const MessageScroll = styled.div`
  max-height: 660px;
  overflow-y: scroll;
  padding-left: 5px;
`

export const ReadAllHeading = styled.h4`
  cursor: pointer;
  margin-bottom: 4px;
  display: flex;
  justify-content: flex-end;
  font-size: 13px;
  font-weight: lighter;
  &:hover {
    color: ${color1};
    cursor: pointer;
  }
`;

export const MessageList = styled.ul`
  list-style-type: none;
  padding: 0;
  margin: 0;
`;

export const MessageItem = styled.li<{ read: boolean }>`
  background-color: ${(props) => (props.read ? `${colorWW}` : `${colorW}`)};
  cursor: pointer;
  width: 100%;
  border-bottom: 1px solid ${colorG};
  border-radius: 4px;
  // box-shadow: 1px ${colorG};
  margin-top: 2px;
  padding: 10px 5px; 

  &:hover {
    color: ${color1};
    cursor: pointer;
  }
`;

export const WatchMore = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  border-top: 1px solid ${colorG};
  padding: 15px;
  font-size: 13px;
  color: ${color3};

  &:hover {
    color: ${color1};
    cursor: pointer;
  }
`

export const MessageHeader = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 10px 5px;
`

export const HeaderTitle = styled.div`
  font-weight: 500;
  font-size: large;
  margin-left: 4px;
  width: 
`
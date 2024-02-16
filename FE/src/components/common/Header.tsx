import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import FetchAllMessages from "../../apis/alarm/FetchAllMessages.tsx";
import useLogout from "../../apis/member/useLogout";
import wondoologo from "../../assets/icon/WondooLogo.png";
import basicProfile from "../../assets/icon/basicProfile.png";
import bell from "../../assets/icon/headerBell.png";
import DM from "../../assets/icon/headerDM.png";
import logo from "../../assets/icon/logo.png";
import { DropdownState } from "../../interfaces/DropdownProps";
import { ContentObject } from "../../interfaces/alarm/HeaderAlarm";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store.tsx";
import createMessage from "../alarm/CreateMessage.tsx";
import Login from "../signup/Login";
import Dropdown from "./Dropdown";
import Messages from "./HeaderMessages";
import Signal from "./HeaderSignal";
import {
  Icon,
  Logo,
  LogoWrapper,
  ProfileIcon,
  QuickMenu,
  Wrapper,
} from "./HeaderStyle";
import Search from "./Search";

const Header = () => {
  const [allMessages, setAllMessages] = useState<ContentObject[]>([]);
  const [messageNum, setMessageNum] = useState<number>(0); // 쌓인 알림 개수
  const [showMessages, setShowMessages] = useState(false); // 알림 클릭하면 메시지들 보여주기 위해
  const [heartBeat, setHeartBeat] = useState<number>(1000000000000000);

  const access_token = localStorage.getItem("accessToken");
  const navigate = useNavigate();
  const messagesRef = useRef<HTMLDivElement>(null);
  const member_id = useAppSelector(
    (state: RootState) => state.user.userInfo?.member_id
  );
  const image_id = useAppSelector(
    (state: RootState) => state.user.userInfo?.image_id
  );
  const url = `https://noti.wondoo.kr/notification-service/notification/subscribe/${member_id}`;
  const logout = useLogout();

  const [imageUrl, setImageUrl] = useState(
    `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
  );

  useEffect(() => {
    setImageUrl(
      `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${image_id}`
    );
  }, [image_id]);

  useEffect(() => {
    const closeMessagesOnOutsideClick = (event: MouseEvent) => {
      const messagesContainer = messagesRef.current;
      const target = event.target as Node;

      if (messagesContainer && !messagesContainer.contains(target)) {
        setShowMessages(false);
      }
    };

    document.addEventListener("click", closeMessagesOnOutsideClick);

    return () => {
      document.removeEventListener("click", closeMessagesOnOutsideClick);
    };
  }, []);

  useEffect(() => {
    // 렌더링 시 SSE 연결 요청
    fetchSSE();
  }, []);

  useEffect(() => {
    // SSE 재연결 요청 로직
    const initialHeartBeat = heartBeat;

    const intervalId = setInterval(() => {
      const currentTime = Date.now();

      if (initialHeartBeat !== heartBeat) {
        // console.log("HeartBeat changed. Stopping interval.");
        clearInterval(intervalId);
        return;
      }

      if (currentTime > initialHeartBeat) {
        // console.log("SSE 재요청");
        fetchSSE();
        clearInterval(intervalId);
      }
    }, 1000);

    return () => clearInterval(intervalId);
  }, [heartBeat]);

  const fetchSSE = () => {
    const eventSource = new EventSource(url); // 이벤트 소스 생성

    eventSource.onopen = () => {
      // SSE 연결 시 할 일들
      console.log("SSE opened");
    };

    eventSource.onmessage = async (e: MessageEvent) => {
      // SSE 메시지 수신 시 할 일들
      const res = await e.data;
      const eventData = JSON.parse(res);
      console.log(eventData);
      if (eventData.heartbeat !== undefined) {
        setHeartBeat(eventData.heartbeat + 30000);
      } else {
        setMessageNum(eventData.unread_count);
        const filtered_message = createMessage(eventData);
        console.log(filtered_message);
        setAllMessages((prevMessages) => [...prevMessages, filtered_message]);
      }
    };

    eventSource.onerror = (e: Event) => {
      eventSource.close();
      console.error("Error wirh SSE: " + e, e.target);
    };
  };

  const showMessageDropdown = async () => {
    // 알림 클릭 시 메시지 보이게 하기
    const allContent = await FetchAllMessages(access_token);
    const newMessages = allContent.map((message) => createMessage(message));
    setAllMessages(newMessages);
    setShowMessages(!showMessages);
  };

  // 드롭 다운 로직
  const [state, setState] = useState<DropdownState>({
    position: {
      top: "50px",
      right: "0px",
    },
    size: {
      width: 200,
      height: 150,
    },
    buttons: [
      {
        useFunction: () => {
          navigate(`/member/${member_id}`);
        },
        content: "마이페이지",
      },
      {
        useFunction: () => {
          logout();
        },
        content: "로그아웃",
      },
      {
        useFunction: () => {
          navigate("/setting");
        },
        content: "설정",
      },
    ],
    isActive: false,
  });
  const children = (
    <ProfileIcon
      src={image_id ? imageUrl : basicProfile}
      alt="마이 메뉴"
      className="image"
    />
  );

  return (
    <Wrapper>
      <Link to="/">
        <LogoWrapper>
          <Icon src={logo} alt="홈으로" />
          <Logo src={wondoologo} alt="로고" width="80px" />
        </LogoWrapper>
      </Link>
      <div>
        <Search />
        {access_token ? (
          <QuickMenu>
            <Link to="/chat">
              <Icon src={DM} alt="메세지" />
            </Link>
            <div ref={messagesRef}>
              <Icon src={bell} alt="알람" onClick={showMessageDropdown} />
              <Signal messageNum={messageNum} />
              {showMessages && (
                <Messages
                  member_id={member_id}
                  setMessageNum={setMessageNum}
                  showMessages={showMessages}
                  allMessages={allMessages}
                  setAllMessages={setAllMessages}
                />
              )}
            </div>
            <Dropdown state={state} setState={setState} children={children} />
          </QuickMenu>
        ) : (
          <Login />
        )}
      </div>
    </Wrapper>
  );
};

export default Header;

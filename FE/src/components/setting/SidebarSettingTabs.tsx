import { color2 } from "../../constants/colors";
import { styled } from "../../constants/styled";
import infoSettingIcon from "../../assets/icon/basicProfile.png";
import alarmSettingIcon from "../../assets/icon/headerBell.png";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const DivTabsContainer = styled.div`
  .selected {
    border-bottom: 5px solid ${color2};
  }
`;

const DivTabContainer = styled.div`
  border-bottom: 1px solid ${color2};
  display: flex;
  margin: 10px;

  .settingSidebarIcon {
    width: 30px;
    margin: 10px;
  }

  .settingSidebarContent {
    margin: auto 0;
    padding: 0;
    font-weight: 500;
  }

  :hover {
    cursor: pointer;
  }

  &.selected {
    .settingSidebarContent {
      font-weight: bold;
    }
  }
`;

const SidebarSettingTab = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [nowSettingPage, setNowSettingPage] = useState<string>(
    location.pathname.slice(9)
  );

  const handleClick = (selectedSettingPage: string) => {
    navigate(`/setting/${selectedSettingPage}`);
    setNowSettingPage(selectedSettingPage);
  };
  return (
    <DivTabsContainer>
      <DivTabContainer
        className={nowSettingPage === "" ? "selected" : undefined}
        onClick={() => handleClick("")}
      >
        <img src={infoSettingIcon} className="settingSidebarIcon" />
        <p className="settingSidebarContent">개인정보</p>
      </DivTabContainer>
      <DivTabContainer
        className={nowSettingPage === "alarm" ? "selected" : undefined}
        onClick={() => handleClick("alarm")}
      >
        <img src={alarmSettingIcon} className="settingSidebarIcon" />
        <p className="settingSidebarContent">알림</p>
      </DivTabContainer>
      <DivTabContainer
        className={nowSettingPage === "theme" ? "selected" : undefined}
        onClick={() => handleClick("theme")}
      >
        <img src={infoSettingIcon} className="settingSidebarIcon" />
        <p className="settingSidebarContent">테마</p>
      </DivTabContainer>
    </DivTabsContainer>
  );
};

export default SidebarSettingTab;

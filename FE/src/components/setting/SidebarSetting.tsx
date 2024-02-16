import { color2, colorW } from "../../constants/colors";
import { styled } from "../../constants/styled";
import SidebarSettingTabs from "./SidebarSettingTabs";

const DivSettingContainer = styled.div`
  border-right: 1px solid ${color2};
  background-color: ${colorW};
`;
const SidebarSetting = () => {
  return (
    <DivSettingContainer>
      <SidebarSettingTabs />
    </DivSettingContainer>
  );
};

export default SidebarSetting;

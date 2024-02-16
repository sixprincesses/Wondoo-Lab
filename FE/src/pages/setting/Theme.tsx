import {
  DivSettingContainer,
  DivSettingInput,
} from "../../components/setting/SettingPageStyle";
import defaultTheme from "../../assets/img/defaultTheme.png";
import darkTheme from "../../assets/img/darkTheme.png";
import { useState } from "react";

const Theme = () => {
  const [isSelected, setIsSelected] = useState("default");

  const handleTheme = (selectedTheme: string) => {
    setIsSelected(selectedTheme);
  };
  return (
    <DivSettingContainer>
      <div className="frame"></div>
      <div>
        <h1 className="title">테마</h1>
        <DivSettingInput height={"500px"}>
          <div
            className={`theme-div ${
              isSelected === "default" ? "isSelected" : null
            }`}
          >
            <h3>기본 테마</h3>
            <img
              className="theme-image default-theme"
              onClick={() => handleTheme("default")}
              src={defaultTheme}
            />
          </div>
          <div
            className={`theme-div ${
              isSelected === "dark" ? "isSelected" : null
            }`}
          >
            <h3>다크 테마</h3>
            <img
              className="theme-image"
              onClick={() => handleTheme("dark")}
              src={darkTheme}
            />
          </div>
        </DivSettingInput>
      </div>
      <div className="frame"></div>
    </DivSettingContainer>
  );
};

export default Theme;

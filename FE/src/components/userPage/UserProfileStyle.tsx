import { color3, colorB, colorG, colorW } from "../../constants/colors";
import { styled } from "../../constants/styled";

const DivUserProfileContainer = styled.div`
  position: relative;
  width: 1020px;
  margin: 0 auto 20px auto;
  padding-bottom: 20px;
  background-color: ${colorW};
  border-bottom: 3px;
`;

const DivImageContainer = styled.div`
  position: relative;
  height: 270px;
  overflow: hidden;
  object-fit: cover;

  input {
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    outline: none;
    border: none;
  }

  input[type="file"] {
    position: relative;
    opacity: 0;
    &:hover {
      cursor: pointer;
    }
  }

  .profile-background-image {
    position: absolute;
    margin: 0;
    padding: 0;
    overflow: hidden;
    object-fit: cover;
    width: 100%;
    height: 207px;
  }

  // .profile-background-input {
  //   position: absolute;
  //   width: 1020px;
  //   height: 270px;
  //   margin: 0;
  //   padding: 0;
  //   z-index: 1;
  // }

  .profile-image {
    position: absolute;
    background: ${colorW};
    width: 137px;
    height: 137px;
    top: 133px;
    left: 25px;
    border: 1px solid ${color3};
    border-radius: 100%;
    z-index: 2;
  }

  .profile-input {
    position: absolute;
    width: 137px;
    height: 137px;
    top: 133px;
    left: 25px;
    border-radius: 100%;
    z-index:3;
  }
`;

const ProfileBackInput = styled.input`
  position: absolute;
  top: 200px;
  right: 5px;
  opacity: 0;
  z-index: 1;
`;

const CustomInputLabel = styled.label`
  position: absolute;
  top: 193px;
  right: 5px;
  padding: 8px 12px;
  background-color: ${color3};
  border: 1px solid ${colorG};
  border-radius: 5px;
  color: ${colorW};
  font-size: 14px;
  cursor: pointer;

  &:hover {
    cursor: pointer;
  }
`;

const DivProfileInfoContainer = styled.div`
  margin-top: 3px;
  margin-left: 20px;

  > * {
    margin-bottom: 5px;
  }

  .nickname-row {
    display: flex;
    justify-content: space-between;
    & > div {
      display: flex;
    }
  }

  button {
    width: 70px;
    line-height: 30px;
    background: ${color3};
    color: ${colorW};
    border: 1px solid ${colorG};
    border-radius: 5px;
    margin: 0 5px;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }

  .email {
    color: ${color3};
  }

  .location-date {
    display: flex;
    font-size: 15px;
    color: ${colorB};
  }

  .location-date * {
    margin-right: 5px;
  }

  .follow span {
    color: ${colorB};
    margin-right: 5px;
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }

  .grade-image {
    width: 30px;
    height: 30px;
    margin: auto 5px;
  }
`;

export { DivImageContainer, DivProfileInfoContainer, CustomInputLabel, ProfileBackInput, DivUserProfileContainer };

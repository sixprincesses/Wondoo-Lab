import styled from '@emotion/styled';
import { color1, color2, color3, colorB, colorG, colorW } from "../constants/colors.ts";

const FormContainer = styled.div`
  width: 400px;
  min-height: auto;
  background-color: ${colorW};
  margin: auto;
  margin-top: 20px;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
  padding: 20px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const RegisterForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const SuccessMessage = styled.div`
  font-family: "Roboto", sans-serif;
  background-color: ${color2};
  padding: 15px;
  color: ${colorW};
  text-align: center;
  border-radius: 8px;
  margin-top: 20px;
`;

const FormField = styled.input`
  margin: 10px 5px;
  padding: 15px;
  font-size: 16px;
  border: 0;
  font-family: "Roboto", sans-serif;
  background-color: ${colorG};
  border-radius: 8px;
  width: 100%;
`;

const ErrorMessage = styled.span`
  font-family: "Roboto", sans-serif;
  font-size: 14px;
  color: red;
  margin-bottom: 15px;
`;

const SubmitButton = styled.button`
  background: ${color1};
  color: ${colorW};
  cursor: pointer;
  padding: 15px;
  font-size: 16px;
  border: 0;
  font-family: "Roboto", sans-serif;
  border-radius: 8px;
  width: 100%;
  margin-top: 15px;

  &:hover {
    background: ${color3};
  }
`;

const GenderFormField = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  // background-color: ${colorG};
  border-radius: 10px;
  margin: 10px 0;
  width: 70%;

  label {
    margin-bottom: 5px;
  }

  input {
    margin: 0px 20px;
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    outline: none;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    background: ${color1};
    color: ${colorW};
    width: 120px;
    height: 50px;
    font-size: 15px;
  }

  .genderForm {
    background-color: ${color1};
  }

  .pressed {
    background-color: ${color2};
  }
`;

const ProfileImageField = styled.label`
  display: block;
  margin: 20px 0;
  font-size: 18px;
  padding: 12px 20px;
  font-weight: bold;
  color: ${colorW};
  background-color: ${color1};
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  input[type="file"] {
    display: none; 
  }

  .custom-file-upload {
    width: 1px !important;
    height: 1px;
  }

  &:hover {
    background-color: ${color3};
  }
`;

const ProfileImage = styled.div`
  // width: 250px;
  // height: 250px;
`

const Skip = styled.div`
  width: 100%;
  display: flex;
  margin-top: 40px;
  justify-content: flex-end;

  input {
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    outline: none;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    background: ${colorG};
    color: ${colorB};
    width: 120px;
    height: 50px;
    font-size: 12px;
  }
`

export { FormContainer, RegisterForm, SuccessMessage, FormField, ErrorMessage, SubmitButton, GenderFormField, ProfileImageField, ProfileImage, Skip };
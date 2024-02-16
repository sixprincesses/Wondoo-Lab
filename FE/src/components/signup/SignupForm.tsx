import React, { ChangeEvent, FormEvent, useState } from "react";
import {
  ErrorMessage,
  FormContainer,
  FormField,
  GenderFormField,
  RegisterForm,
  SubmitButton,
  Skip
} from "../../pages/SignupStyle.tsx";
import { useNavigate } from "react-router-dom";

interface SignUpFormProps {
  onSubmit: (e: FormEvent<HTMLFormElement>) => void;
  submitted: boolean;
  values: {
    nickname: string;
    Name: string;
    email: string;
    phone: string;
    gender: string;
  };
  setValues: React.Dispatch<
    React.SetStateAction<{
      nickname: string;
      Name: string;
      email: string;
      phone: string;
      gender: string;
    }>
  >;
}

const SignUpForm = ({
  onSubmit,
  submitted,
  values,
  setValues,
}: SignUpFormProps) => {
  const [selectedGender, setSelectedGender] = useState<string | null>(null);
  // const [imagePreview, setImagePreview] = useState<string | null>(null); // State to store the image preview URL
  const navigate = useNavigate();

  // const allowedImageTypes = [
  //   "image/jpeg",
  //   "image/jpg",
  //   "image/png",
  //   "image/gif",
  //   "image/psd",
  //   "image/tif",
  // ];

  const isChangeEvent = (
    event:
      | ChangeEvent<HTMLInputElement>
      | React.MouseEvent<HTMLInputElement, MouseEvent>
  ): event is ChangeEvent<HTMLInputElement> => {
    return "target" in event && event.target instanceof HTMLInputElement;
  };

  const handleInputChange = (
    event:
      | ChangeEvent<HTMLInputElement>
      | React.MouseEvent<HTMLInputElement, MouseEvent>
  ) => {
    if (isChangeEvent(event)) {
      const { name, value } = event.target;

      if (name === "nickname") {
        // 닉네임 제약조건 체크
        const isValidNickname = /^[가-힣A-Za-z][가-힣A-Za-z0-9-]{1,40}$/.test(
          value
        );

        if (!isValidNickname) {
          setValues((values) => ({
            ...values,
            [name]: value,
          }));

          return;
        }
      }

      if (name === "phone") {
        const isValidPhone = /^\d{11}$/.test(value);

        if (!isValidPhone) {
          setValues((prevValues) => ({
            ...prevValues,
            [name]: value,
          }));
        }
      }

      if (name === "gender") {
        setSelectedGender(value);
      }

      setValues(prevValues => ({
        ...prevValues,
        [name]: value,
      }));

      // if (type === "file") {
      //   // 제대로 된 이미지 파일을 제출하는지 확인
      //   const file = event.target.files?.[0];

      //   if (file && allowedImageTypes.includes(file.type)) {
      //     setValues((values) => ({
      //       ...values,
      //       [name]: file,
      //     }));
      //     setImagePreview(URL.createObjectURL(file));
      //   } else {
      //     setValues((values) => ({
      //       ...values,
      //       [name]: null,
      //     }));
      //     // console.log('Invalid file type. Only jpg, jpeg, png, gif, psd, and tif are allowed.');
      //   }
      // } else {
      //   setValues((values) => ({
      //     ...values,
      //     [name]: value,
      //   }));
      // }
    }
  };

  return (
    <FormContainer>
      <RegisterForm onSubmit={onSubmit} autoComplete="off">
        <FormField
          type="text"
          placeholder="닉네임"
          name="nickname"
          value={values.nickname}
          onChange={handleInputChange}
        />

        {submitted && !values.nickname && (
          <ErrorMessage id="nickname-error">
            Please enter a nickname
          </ErrorMessage>
        )}

        {submitted &&
          values.nickname &&
          !/^[가-힣A-Za-z][가-힣A-Za-z0-9-]{1,40}$/.test(values.nickname) && (
            <ErrorMessage id="nickname-error">
              Nickname must be 2-40 characters without special characters, and
              the first character must be Korean or English.
            </ErrorMessage>
          )}

        <FormField
          type="text"
          placeholder="이름"
          name="Name"
          value={values.Name}
          onChange={handleInputChange}
        />

        {submitted && !values.Name && (
          <ErrorMessage id="first-name-error">Please enter a name</ErrorMessage>
        )}

        <FormField
          type="email"
          placeholder="Email"
          name="email"
          value={values.email}
          onChange={handleInputChange}
        />

        {submitted && !values.email && (
          <ErrorMessage id="email-error">
            Please enter an email address
          </ErrorMessage>
        )}
        <FormField
          type="tel"
          placeholder="Phone ('-'없이 입력하세요)"
          name="phone"
          maxLength={11}
          value={values.phone}
          onChange={handleInputChange}
        />

        {submitted && !values.phone && (
          <ErrorMessage id="phone-error">
            Please enter a phone number
          </ErrorMessage>
        )}

        {submitted && values.phone && !/^\d{11}$/.test(values.phone) && (
          <ErrorMessage id="nickname-error">
            Phone Numbers should be 11 numbers, without hyphens
          </ErrorMessage>
        )}

        <GenderFormField>
          <label>
            <input
              type="button"
              name="gender"
              value="male"
              onClick={(e) => handleInputChange(e)}
              className={`${
                selectedGender === "male" ? "pressed" : "genderForm"
              }`}
            />
          </label>
          <label>
            <input
              type="button"
              name="gender"
              value="female"
              onClick={(e) => handleInputChange(e)}
              className={`${
                selectedGender === "female" ? "pressed" : "genderForm"
              }`}
            />
          </label>
        </GenderFormField>

        {submitted && !values.gender && (
          <ErrorMessage id="gender-error">Please select a gender</ErrorMessage>
        )}

        {/* <ProfileImageField>
          <input
            type="file"
            accept="image/*"
            name="profileImage"
            onChange={handleInputChange}
          />
          <span className="custom-file-upload">프로필 이미지</span>
        </ProfileImageField> */}

        {/* {imagePreview && (
          <ProfileImage>
            <img src={imagePreview} alt="Profile" width="50px" />
          </ProfileImage>
        )} */}
        
        <SubmitButton type="submit">Register</SubmitButton>

        <Skip>
          <input 
            type="button"
            value="다음에 변경하기"
            onClick={() => navigate("/")}
          />
        </Skip>
      </RegisterForm>
    </FormContainer>
  );
};

export default SignUpForm;

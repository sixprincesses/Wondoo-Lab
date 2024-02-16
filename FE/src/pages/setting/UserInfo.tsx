import { useState } from "react";
import {
  ButtonInfoSubmit,
  DivSettingContainer,
  DivSettingInput,
  DivSettingInputContainer,
  SpanSettingErrorMsg,
} from "../../components/setting/SettingPageStyle";
import { useAppSelector } from "../../store/hooks";
import usePutMember from "../../apis/member/usePutMember";

const UserInfo = () => {
  const userInfo = useAppSelector((state) => state.user.userInfo);

  const { name, gender, nickname, email, phone, image_id } = userInfo;
  const [values, setValues] = useState({
    nickname,
    name,
    email,
    phone,
    gender,
    image_id,
  });

  const putMember = usePutMember();
  const [canEdit, setCanEdit] = useState(false);
  const [isEmptyName, setIsEmptyName] = useState(name ? false : true);
  const [isEmptyGender, setIsEmptyGender] = useState(gender ? false : true);
  const [isValidGender, setIsValidGender] = useState(gender ? true : false);
  const [isValidNickname, setIsValidNickname] = useState(true);
  const [isValidPhone, setIsValidPhone] = useState(true);

  const handleUserName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userName = e.target.value;
    setValues((values) => ({
      ...values,
      name: userName,
    }));
  };

  const handleUserGender = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userGender = e.target.value;
    setValues((values) => ({
      ...values,
      gender: userGender,
    }));
    const isValid = /^(male|female)$/.test(userGender);
    if (isValid) {
      setIsValidGender(true);
    } else {
      setIsValidGender(false);
    }
  };

  const handleUserNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userNickname = e.target.value;
    setValues((prev) => ({
      ...prev,
      nickname: userNickname,
    }));
    const isValid = /^[가-힣A-Za-z][가-힣A-Za-z0-9-]{1,40}$/.test(userNickname);

    if (isValid) {
      setIsValidNickname(true);
    } else {
      setIsValidNickname(false);
    }
  };

  const handleUserEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userEmail = e.target.value;
    setValues((prev) => ({
      ...prev,
      email: userEmail,
    }));
  };

  const handleUserPhoneNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    const userPhoneNumber = e.target.value;
    setValues((prev) => ({
      ...prev,
      phone: userPhoneNumber,
    }));
    const isValid = /^\d{11}$/.test(userPhoneNumber);

    if (isValid) {
      setIsValidPhone(true);
    } else {
      setIsValidPhone(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (canEdit) {
      setIsEmptyGender(false);
      setIsEmptyName(false);

      // const formData = new FormData();
      const jsonData = {
        nickname: values.nickname,
        name: values.name,
        email: values.email,
        phone: values.phone,
        gender: values.gender,
      };

      putMember(jsonData);
    }
    setCanEdit((prev) => !prev);
  };
  return (
    <DivSettingContainer>
      <div className="frame"></div>
      <DivSettingInputContainer>
        <h1 className="title">개인 정보</h1>
        <form onSubmit={handleSubmit}>
          <DivSettingInput height={"60px"}>
            <label className="infoSettingLabel" htmlFor="userName">
              이름
            </label>
            <input
              className="infoSettingInput"
              type="text"
              value={values.name}
              onChange={handleUserName}
              readOnly={canEdit && isEmptyName ? false : true}
              id="userName"
            />
          </DivSettingInput>
          {!values.name ? (
            <SpanSettingErrorMsg>이름을 입력해주세요.</SpanSettingErrorMsg>
          ) : null}
          <DivSettingInput height={"60px"}>
            <label className="infoSettingLabel" htmlFor="userGender">
              성별
            </label>
            <input
              className="infoSettingInput"
              type="text"
              value={values.gender}
              maxLength={6}
              onChange={handleUserGender}
              readOnly={canEdit && isEmptyGender ? false : true}
              id="userGender"
            />
          </DivSettingInput>
          {!isValidGender ? (
            <SpanSettingErrorMsg>
              성별은 "male" 또는 "female"만 입력 가능합니다.
            </SpanSettingErrorMsg>
          ) : null}
          <DivSettingInput height={"60px"}>
            <label className="infoSettingLabel" htmlFor="userNickname">
              닉네임
            </label>
            <input
              className="infoSettingInput"
              type="text"
              value={values.nickname}
              onChange={handleUserNickname}
              readOnly={canEdit ? false : true}
              id="userNickname"
              autoComplete="off"
            />
          </DivSettingInput>
          {!isValidNickname && (
            <SpanSettingErrorMsg>
              닉네임은 특수문자 없이 2~40자까지 가능합니다.
            </SpanSettingErrorMsg>
          )}
          <DivSettingInput height={"60px"}>
            <label className="infoSettingLabel" htmlFor="userEmail">
              이메일
            </label>
            <input
              className="infoSettingInput"
              type="text"
              value={values.email}
              onChange={handleUserEmail}
              readOnly={canEdit ? false : true}
              id="userEmail"
              autoComplete="off"
            />
          </DivSettingInput>
          {!values.email && (
            <SpanSettingErrorMsg>이메일을 입력해주세요.</SpanSettingErrorMsg>
          )}
          <DivSettingInput height={"60px"}>
            <label className="infoSettingLabel" htmlFor="userPhoneNumber">
              휴대폰 번호
            </label>
            <input
              className="infoSettingInput"
              type="tel"
              maxLength={11}
              value={values.phone}
              onChange={handleUserPhoneNumber}
              readOnly={canEdit ? false : true}
              id="userPhoneNumber"
              autoComplete="off"
            />
          </DivSettingInput>
          {(!values.phone || !isValidPhone) && (
            <SpanSettingErrorMsg>
              전화 번호는 하이픈(-) 없이 11자리의 숫자여야 합니다.
            </SpanSettingErrorMsg>
          )}
          <ButtonInfoSubmit
            type="submit"
            disabled={
              canEdit &&
              (!isValidNickname ||
                !isValidPhone ||
                !values.email ||
                !isValidGender ||
                !values.name)
            }
          >
            {canEdit ? "저장" : "수정"}{" "}
          </ButtonInfoSubmit>
        </form>
      </DivSettingInputContainer>
      <div className="frame"></div>
    </DivSettingContainer>
  );
};

export default UserInfo;

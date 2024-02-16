import { FormEvent, useState } from "react";
import usePutMember from "../apis/member/usePutMember";
import SignUpForm from "../components/signup/SignupForm";

const SignUp = () => {
  const putMember = usePutMember();

  const [values, setValues] = useState({
    nickname: "",
    Name: "",
    email: "",
    phone: "",
    gender: "",
    // profileImage: null as File | null,
  });
  const [submitted, setSubmitted] = useState<boolean>(false);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!values.nickname) return;
    if (!values.Name) return;
    if (!values.email) return;
    if (!values.phone) return;
    if (!values.gender) return;

    // 데이터 파싱
    // const formData = new FormData();
    const jsonData = {
      nickname: values.nickname,
      name: values.Name,
      email: values.email,
      phone: values.phone,
      gender: values.gender,
    };
    // formData.append(
    //   "json",
    //   new Blob([JSON.stringify(jsonData)], { type: "application/json" })
    // );
    // if (values.profileImage !== null) {
    //   formData.append("image_id", values.profileImage);
    // }

    // 통신 함수 호출
    putMember(jsonData);
    setSubmitted(true);
  };

  return (
    <SignUpForm
      onSubmit={handleSubmit}
      submitted={submitted}
      values={values}
      setValues={setValues}
    />
  );
};

export default SignUp;

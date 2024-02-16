import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { userState } from "../interfaces/member/UserState";
import { LoginResponse } from "../interfaces/signup/LoginResponse";
import { RootState } from "./store";

const initialState: userState = {
  userInfo: {
    member_id: -1,
    image_id: "",
    nickname: "",
    email: "",
    name: "",
    phone: "",
    gender: "",
    level: "",
    access_token: "",
    refresh_token: "",
  },
};

const userSlice = createSlice({
  name: "userSlice",
  initialState,
  reducers: {
    login: (state, action: PayloadAction<LoginResponse>) => {
      state.userInfo = action.payload;
    },
    setImageId: (state, action: PayloadAction<string>) => {
      state.userInfo.image_id = action.payload;
    },
    patchMember: (state, action) => {
      state.userInfo = {
        ...state.userInfo,
        ...action.payload,
      };
    },
    setRefreshToken: (
      state,
      action: PayloadAction<{ access_token: string; refresh_token: string }>
    ) => {
      state.userInfo.access_token = action.payload.access_token;
      state.userInfo.refresh_token = action.payload.refresh_token;
    },
  },
});

export const { login, setImageId, patchMember, setRefreshToken } =
  userSlice.actions;
export const selectUserState = (state: RootState) => state.user;
export default userSlice;

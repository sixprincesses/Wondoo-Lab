import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { content, followState } from "../interfaces/member/FollowState";
import { RootState } from "./store";

interface followPayload {
  content: content[];
  isEnd: boolean;
  page: number;
}

const initialState: followState = {
  followers: {
    members: [],
    isEnd: false,
    isFetching: false,
    page: 0,
    size: 100,
  },
  followings: {
    members: [],
    isEnd: false,
    isFetching: false,
    page: 0,
    size: 100,
  },
  friends: {
    members: [],
    isEnd: false,
    isFetching: false,
    page: 0,
    size: 100,
  },
};

const followSlice = createSlice({
  name: "followSlice",
  initialState,
  reducers: {
    setFollowers: (state, action: PayloadAction<followPayload>) => {
      state.followers.members.push(...action.payload.content);
      state.followers.isEnd = action.payload.isEnd;
      state.followers.page = action.payload.page;
    },
    initializeFollowers: (state) => {
      state.followers = {
        members: [],
        isEnd: false,
        isFetching: false,
        page: 0,
        size: 100,
      };
    },
    setFollowersFetching: (state, action: PayloadAction<boolean>) => {
      state.followers.isFetching = action.payload;
    },
    setFollowings: (state, action: PayloadAction<followPayload>) => {
      state.followings.members.push(...action.payload.content);
      state.followings.isEnd = action.payload.isEnd;
      state.followers.page = action.payload.page;
    },
    initializeFollowings: (state) => {
      state.followings = {
        members: [],
        isEnd: false,
        isFetching: false,
        page: 0,
        size: 100,
      };
    },
    setFollowingsFetching: (state, action: PayloadAction<boolean>) => {
      state.followings.isFetching = action.payload;
    },
    setFriends: (state, action: PayloadAction<followPayload>) => {
      state.friends.members.push(...action.payload.content);
      state.friends.isEnd = action.payload.isEnd;
      state.followers.page = action.payload.page;
    },
    initializeFriends: (state) => {
      state.friends = {
        members: [],
        isEnd: false,
        isFetching: false,
        page: 0,
        size: 100,
      };
    },
    setFriendsFetching: (state, action: PayloadAction<boolean>) => {
      state.friends.isFetching = action.payload;
    },
  },
});

export const {
  setFollowers,
  initializeFollowers,
  setFollowersFetching,
  setFollowings,
  initializeFollowings,
  setFollowingsFetching,
  setFriends,
  initializeFriends,
  setFriendsFetching,
} = followSlice.actions;
export const selectFollowState = (state: RootState) => state.follow;
export default followSlice;

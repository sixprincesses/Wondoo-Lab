import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { rank } from "../interfaces/feed/FeedState";
import { GetFeedsResponse } from "../interfaces/feed/GetFeedsResponse";
import { GetBookmarkResponse } from "../interfaces/feed/GetookmarkResponse";
import { memberInfo, memberState } from "../interfaces/member/MemberState";
import { RootState } from "./store";

const initialState: memberState = {
  memberInfo: {
    member_id: -1,
    image_id: "",
    cover_image_id: "",
    nickname: "",
    name: "",
    email: "",
    phone: "",
    gender: "",
    created_time: new Date(),
    follower_count: -1,
    following_count: -1,
    is_follow: false,
    level: "BEAN_1",
  },
  feed: {
    memberFeeds: [],
    last_feed_id: null,
    isEnd: false,
    isFetching: false,
  },
  bookmark: {
    bookmarks: [],
    last_bookmark_id: null,
    isEnd: false,
    isFetching: false,
  },
  rank: [],
};

const memberSlice = createSlice({
  name: "memberSlice",
  initialState,
  reducers: {
    setMember: (state, action: PayloadAction<memberInfo>) => {
      state.memberInfo = action.payload;
    },
    setImage: (state, action: PayloadAction<string>) => {
      state.memberInfo.image_id = action.payload;
    },
    setCover: (state, action: PayloadAction<string>) => {
      state.memberInfo.cover_image_id = action.payload;
    },
    setIsFollow: (state, action: PayloadAction<boolean>) => {
      state.memberInfo.is_follow = action.payload;
    },
    setMemberFeeds: (state, action: PayloadAction<GetFeedsResponse>) => {
      state.feed.memberFeeds.push(...action.payload.feeds);
      state.feed.last_feed_id = action.payload.last_feed_id;
      if (action.payload.last_feed_id === -1) {
        state.feed.isEnd = true;
      }
    },
    initializeMemberFeeds: (state) => {
      state.feed = {
        memberFeeds: [],
        last_feed_id: null,
        isEnd: false,
        isFetching: false,
      };
    },
    setBookmarksFetching: (state, action: PayloadAction<boolean>) => {
      state.bookmark.isFetching = action.payload;
    },
    setBookmarks: (state, action: PayloadAction<GetBookmarkResponse>) => {
      state.bookmark.bookmarks.push(...action.payload.bookmarks);
      state.bookmark.last_bookmark_id = action.payload.last_bookmark_id;
      if (action.payload.last_bookmark_id === -1) {
        state.bookmark.isEnd = true;
      }
    },
    initializeBookmarks: (state) => {
      state.bookmark = {
        bookmarks: [],
        last_bookmark_id: null,
        isEnd: false,
        isFetching: false,
      };
    },
    setMemberFeedsFetching: (state, action: PayloadAction<boolean>) => {
      state.feed.isFetching = action.payload;
    },
    setRank: (state, action: PayloadAction<rank[]>) => {
      state.rank = action.payload;
    },
  },
});

export const {
  setMember,
  setImage,
  setCover,
  setIsFollow,
  setMemberFeeds,
  initializeMemberFeeds,
  setBookmarksFetching,
  setBookmarks,
  initializeBookmarks,
  setMemberFeedsFetching,
  setRank,
} = memberSlice.actions;
export const selectMemberState = (state: RootState) => state.member;
export default memberSlice;

import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import {
  comment,
  feed,
  feedState,
  keyword,
} from "../interfaces/feed/FeedState";
import { RootState } from "./store";

const initialState: feedState = {
  feeds: {
    feeds: [],
    last_feed_id: null,
    isEnd: false,
    isFetching: false,
  },
  selectedFeed: {
    feed_id: -1,
    title: "",
    content: "",
    member: {
      member_id: -1,
      nickname: "",
      image_id: "",
      level: "",
    },
    time_logs: [],
    total_time: 0,
    created_time: new Date(),
    updated_time: new Date(),
    keywords: [],
    is_liked: false,
    is_bookmarked: false,
  },
  selectedFeedComments: [],
  keywordRanks: [],
};

const feedSlice = createSlice({
  name: "feedSlice",
  initialState,
  reducers: {
    pushFeeds: (
      state,
      action: PayloadAction<{ feeds: feed[]; last_feed_id: number }>
    ) => {
      state.feeds.feeds.push(...action.payload.feeds);
      state.feeds.last_feed_id = action.payload.last_feed_id;
      if (action.payload.last_feed_id === -1) {
        state.feeds.isEnd = true;
      }
    },
    initializeFeeds: (state) => {
      state.feeds = {
        feeds: [],
        last_feed_id: null,
        isEnd: false,
        isFetching: false,
      };
    },
    setIsFetching: (state, action: PayloadAction<boolean>) => {
      state.feeds.isFetching = action.payload;
    },
    selectFeed: (state, action: PayloadAction<feed>) => {
      state.selectedFeed = action.payload;
    },
    setComments: (state, action: PayloadAction<comment[]>) => {
      state.selectedFeedComments = action.payload;
    },
    setKeywordRanks: (state, action: PayloadAction<keyword[]>) => {
      state.keywordRanks = action.payload;
    },
  },
});

export const {
  pushFeeds,
  initializeFeeds,
  setIsFetching,
  selectFeed,
  setComments,
  setKeywordRanks,
} = feedSlice.actions;
export const selectFeedState = (state: RootState) => state.feed;
export default feedSlice;

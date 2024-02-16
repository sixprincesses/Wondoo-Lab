import {
  Reducer,
  ThunkAction,
  UnknownAction,
  combineReducers,
  configureStore,
} from "@reduxjs/toolkit";
import { PersistConfig, persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage";
import { ChatState } from "../interfaces/chat/ChatState";
import { feedState } from "../interfaces/feed/FeedState";
import { followState } from "../interfaces/member/FollowState";
import { memberState } from "../interfaces/member/MemberState";
import { userState } from "../interfaces/member/UserState";
import { tempFeedState } from "../interfaces/tempFeed/TempFeedState";
import chatSlice from "./chatSlice";
import feedSlice from "./feedSlice";
import followSlice from "./followSlice";
import memberSlice from "./memberSlice";
import tempFeedSlice from "./tempFeedSlice";
import userSlice from "./userSlice";

const persistConfig: PersistConfig<any> = {
  key: "root",
  version: 0,
  storage,
  blacklist: ["feed", "member", "follow"],
};

const reducer: Reducer<{
  user: userState;
  follow: followState;
  member: memberState;
  feed: feedState;
  tempFeed: tempFeedState;
  chat: ChatState;
}> = combineReducers({
  user: userSlice.reducer,
  follow: followSlice.reducer,
  member: memberSlice.reducer,
  feed: feedSlice.reducer,
  tempFeed: tempFeedSlice.reducer,
  chat: chatSlice.reducer,
});

const persistedReducer = persistReducer(persistConfig, reducer);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  UnknownAction
>;

export default store;

import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { GetTempFeedRespons } from "../interfaces/tempFeed/GetTempFeedResponse";
import { tempFeedState } from "../interfaces/tempFeed/TempFeedState";
import { RootState } from "./store";

const initialState: tempFeedState = {
  data: {
    title: "",
    keywords: [],
    instanceData: "[]",
    timelogs: [],
    totalTime: 0,
  },
  startTime: new Date(),
  running: false,
};

const tempFeedSlice = createSlice({
  name: "tempFeedSlice",
  initialState,
  reducers: {
    setStartTime: (state) => {
      state.startTime = new Date();
    },
    pushTimeLogs: (state) => {
      const startTime = new Date(state.startTime);
      const endTime = new Date();
      const newTimeLog = { startTime, endTime };
      const prevTimeLogs = state.data.timelogs;
      const newTimeMilisec = Math.abs(startTime.getTime() - endTime.getTime());
      if (newTimeMilisec === 0) {
        state.startTime = new Date();
        return;
      }

      state.data.timelogs = [...prevTimeLogs, newTimeLog];

      const newTime = newTimeMilisec / 1000;
      state.data.totalTime += newTime;

      state.startTime = new Date();
    },
    setRunning: (state, action: PayloadAction<boolean>) => {
      state.running = action.payload;
    },
    initializeTotalTime: (state) => {
      state.data.totalTime = 1;
      state.data.timelogs = [];
    },
    setInstanceData: (
      state,
      action: PayloadAction<{ id: number; data: [string, string] }>
    ) => {
      const arrayFormData: [number, [string, string]][] = JSON.parse(
        state.data.instanceData
      );
      const mapFormJSON = new Map<number, [string, string]>(arrayFormData);
      mapFormJSON.set(action.payload.id, action.payload.data);
      const mapAsString = JSON.stringify(Array.from(mapFormJSON));

      state.data.instanceData = mapAsString;
    },
    setInstanceDataByDrag: (state, action: PayloadAction<string>) => {
      state.data.instanceData = action.payload;
    },
    deleteInstaceData: (state, action: PayloadAction<{ id: number }>) => {
      // instanceData 삭제
      const arrayFormData: [number, string][] = JSON.parse(
        state.data.instanceData
      );
      const mapFormJSON = new Map<number, string>(arrayFormData);
      mapFormJSON.delete(action.payload.id);
      const mapAsString = JSON.stringify(Array.from(mapFormJSON));

      state.data.instanceData = mapAsString;
    },
    fetchData: (state, action: PayloadAction<GetTempFeedRespons>) => {
      state.data.instanceData = action.payload.content;
      state.data.timelogs = action.payload.time_logs;
    },
    initailizeData: (state) => {
      state.data = {
        title: "",
        keywords: [],
        instanceData: "[]",
        timelogs: [],
        totalTime: 0,
      };
    },
  },
});
export const {
  setStartTime,
  pushTimeLogs,
  setRunning,
  initializeTotalTime,
  initailizeData,
  setInstanceData,
  setInstanceDataByDrag,
  deleteInstaceData,
  fetchData,
} = tempFeedSlice.actions;
export const selectTempFeedSlice = (state: RootState) => state.tempFeed;
export default tempFeedSlice;

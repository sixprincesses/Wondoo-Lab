import { useEffect, useRef, useState } from "react";
import pause from "../../assets/icon/pause.png";
import { WatchContainer } from "../../pages/study/StudyStyle.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks.tsx";
import { RootState } from "../../store/store.tsx";
import { pushTimeLogs, setRunning } from "../../store/tempFeedSlice.tsx";
import StartModal from "./StartModal.tsx";
import * as Styled from "./StopwatchStyle.tsx";

const Stopwatch = () => {
  const running = useAppSelector((state: RootState) => state.tempFeed.running);
  const totalTime = useAppSelector(
    (state: RootState) => state.tempFeed.data.totalTime
  );
  const [time, setTime] = useState<number>(0);
  const timeLogs = useAppSelector(
    (state: RootState) => state.tempFeed.data.timelogs
  );
  const [isHovering, setIsHovering] = useState<boolean>(false);
  const intervalRef = useRef<NodeJS.Timeout | null>(null);
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (!totalTime) return;
    setTime(totalTime);
  }, [totalTime]);

  const handlePauseClick = () => {
    if (running) {
      clearInterval(intervalRef.current!);
      dispatch(setRunning(false));
      dispatch(pushTimeLogs());
    }
    setIsHovering(false);
  };

  const handleModalClose = (modalConfirmed: boolean) => {
    if (modalConfirmed) {
      if (!running) {
        intervalRef.current = setInterval(() => {
          setTime((prevTime) => prevTime + 1);
        }, 1000);
      }
    }
  };

  const formatTime = (seconds: number) => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const remainingSeconds = Math.floor(seconds % 60);

    return `${String(hours).padStart(2, "0")}:${String(minutes).padStart(
      2,
      "0"
    )}:${String(remainingSeconds).padStart(2, "0")}`;
  };

  return (
    <WatchContainer>
      {formatTime(time)}
      <Styled.WatchButton
        onClick={handlePauseClick}
        onMouseEnter={() => setIsHovering(true)}
        onMouseLeave={() => setIsHovering(false)}
      >
        {running ? (
          <img src={pause} alt="Pause" width="30px" height="25px" />
        ) : (
          <StartModal onClose={handleModalClose} />
        )}
        {isHovering && timeLogs?.length !== 0 && (
          <Styled.TimeLogDisplay>
            <ol>
              {timeLogs.map((log, index) => (
                <li key={index}>
                  Start Time: {new Date(log.startTime).toLocaleTimeString()},
                  End Time: {new Date(log.endTime).toLocaleTimeString()}
                </li>
              ))}
            </ol>
          </Styled.TimeLogDisplay>
        )}
      </Styled.WatchButton>
    </WatchContainer>
  );
};

export default Stopwatch;

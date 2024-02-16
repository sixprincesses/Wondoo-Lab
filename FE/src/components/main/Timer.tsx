import { useEffect, useState } from "react";
import useFeedLast from "../../apis/feed/useFeedLast.tsx";
import * as Styled from "./TimerStyle";

interface FlipClockProps {
  label: string;
  value: number;
}

const FlipClock = ({ label, value }: FlipClockProps) => {
  const [prevFormattedValue, setPrevFormattedValue] = useState<string>("00");
  const [shouldActivateClass, setShouldActivateClass] =
    useState<boolean>(false);

  const formattedValue = value.toString().padStart(2, "0");

  useEffect(() => {
    if (prevFormattedValue !== null && value !== Number(prevFormattedValue)) {
      setShouldActivateClass(true);
      if (label !== "Seconds") {
        setTimeout(() => {
          setShouldActivateClass(false);
        }, 1500);
      }
    } else {
      setShouldActivateClass(false);
    }
    if (label === "Seconds") {
      setPrevFormattedValue(formattedValue);
    } else {
      setTimeout(() => {
        setPrevFormattedValue(formattedValue);
      }, 1000);
    }
  }, [value, label]);

  return (
    <Styled.FlipClockContainer>
      <Styled.FlipUnitContainer>
        <Styled.FlipUnitValue>{prevFormattedValue}</Styled.FlipUnitValue>
        <Styled.FlipCard
          className={shouldActivateClass ? "FlipCard Fold" : "Hide"}
        >
          <Styled.FlipCard
            className={shouldActivateClass ? "UpperCard" : "Hide"}
          >
            <Styled.FlipUnitValue>
              <span>{prevFormattedValue}</span>
            </Styled.FlipUnitValue>
          </Styled.FlipCard>
          <Styled.FlipCard
            className={shouldActivateClass ? "LowerCard" : "Hide"}
          >
            <Styled.FlipUnitValue>
              <span>{prevFormattedValue}</span>
            </Styled.FlipUnitValue>
          </Styled.FlipCard>
        </Styled.FlipCard>
        <Styled.FlipCard
          className={shouldActivateClass ? "FlipCard Unfold" : "Hide"}
        >
          <Styled.FlipCard
            className={shouldActivateClass ? "UpperCard" : "Hide"}
          >
            <Styled.FlipUnitValue>
              <span>{prevFormattedValue}</span>
            </Styled.FlipUnitValue>
          </Styled.FlipCard>
          <Styled.FlipCard
            className={shouldActivateClass ? "LowerCard" : "Hide"}
          >
            <Styled.FlipUnitValue>
              <span>{prevFormattedValue}</span>
            </Styled.FlipUnitValue>
          </Styled.FlipCard>
        </Styled.FlipCard>
      </Styled.FlipUnitContainer>
    </Styled.FlipClockContainer>
  );
};

const Stopwatch = () => {
  const [isAvailable, setIsAvailable] = useState<boolean>(false);
  const [hours, setHours] = useState<number>(0);
  const [minutes, setMinutes] = useState<number>(0);
  const [seconds, setSeconds] = useState<number>(0);
  const access_token = localStorage.getItem("accessToken");

  const lastfeed = useFeedLast();

  useEffect(() => {
    const fetchLastFeed = async () => {
      try {
        const lastFeedData = await lastfeed();
        console.log("Last Feed Data: ", lastFeedData);

        const currentDate = new Date();
        const lastFeedDate = new Date(lastFeedData.date);
        const difference = currentDate.getTime() - lastFeedDate.getTime();

        const totalSeconds = Math.floor(difference / 1000);
        const totalMinutes = Math.floor(Math.max(totalSeconds / 60, 0));
        const totalHours = Math.floor(Math.max(totalMinutes / 60, 0));

        const remainingSeconds = totalSeconds % 60;
        const remainingMinutes = totalMinutes % 60;

        setHours(totalHours);
        setMinutes(remainingMinutes);
        setSeconds(remainingSeconds);
        setIsAvailable(true);
      } catch (error) {
        console.error("Error fetching last feed: ", error);
        setIsAvailable(false);
      }
    };

    if (access_token) {
      fetchLastFeed();
    }
  }, []);

  useEffect(() => {
    if (minutes === 59 && seconds === 58) {
      setTimeout(() => {
        setHours((prevHours) => prevHours + 1);
        setMinutes(0);
      }, 1000);
    } else if (seconds === 58) {
      setTimeout(() => {
        setMinutes((prevMinutes) => prevMinutes + 1);
      }, 1000);
    } else if (hours > 99) {
      setIsAvailable(false);
    }
  }, [seconds, minutes]);

  useEffect(() => {
    const interval = setInterval(() => {
      setSeconds((prevSeconds) => (prevSeconds + 1) % 60);
      // setMinutes((prevMinutes) => (prevMinutes + Math.floor((seconds + 1) / 60)) % 60);
      // setHours((prevHours) => prevHours + Math.floor((minutes + 0.1) / 60));
    }, 1000);

    return () => clearInterval(interval);
  }, [seconds]);

  return (
    <div>
      {isAvailable ? (
        <Styled.AppContainer>
          <Styled.FlipClockContainer>
            <FlipClock label="Hours" value={hours} />
            <Styled.TimerIntervene> : </Styled.TimerIntervene>
            <FlipClock label="Minutes" value={minutes} />
            <Styled.TimerIntervene> : </Styled.TimerIntervene>
            <FlipClock label="Seconds" value={seconds} />
          </Styled.FlipClockContainer>
        </Styled.AppContainer>
      ) : (
        <Styled.NoData>마지막 피드가 없습니다.</Styled.NoData>
      )}
    </div>
  );
};

export default Stopwatch;

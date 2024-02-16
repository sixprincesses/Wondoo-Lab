import React, { useState, useEffect, useRef } from "react";
import useGetPlanner from "../../apis/member/useGetPlanner";
import { useAppSelector } from "../../store/hooks";
import { FetchedPlannerData } from "../../interfaces/member/UserPlannerData";
import { PlanDetail } from "../../interfaces/member/UserPlannerData";
import * as Styled from "./StudySlideStyle";

function getDayIndex() {
  const today = new Date();
  const dayIndex = today.getDay();
  return (dayIndex + 6) % 7;
}

function getTomorrowIndex() {
  const today = new Date();
  const dayIndex = today.getDay();
  if (dayIndex === 0) {
    return null
  } else {
    return (dayIndex + 6) % 7 + 1;
  }
}

const SlideInMenu: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [timeTableDataList, setTimeTableDataList] = useState<PlanDetail[]>([]);
  const [tomorrowDataList, setTomorrowDataList] = useState<PlanDetail[]>([]);

  const getPlanner = useGetPlanner();
  const memberId = useAppSelector((state) => state.user.userInfo.member_id);
  const currentDayIndex = getDayIndex();
  const tomorrowDayIndex = getTomorrowIndex();
  const menuRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const fetchData = async () => {
      const fetchTimeTableDataList: FetchedPlannerData[] = await getPlanner(+memberId, { week: 0 });
      const newTimeTableDataList = fetchTimeTableDataList[currentDayIndex].planDetails;
      setTimeTableDataList(newTimeTableDataList);
      if (tomorrowDayIndex !== null) {
        const tomorrowDataList = fetchTimeTableDataList[tomorrowDayIndex].planDetails;
        setTomorrowDataList(tomorrowDataList);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      <Styled.MenuContainer ref={menuRef} isOpen={isOpen}>
        <Styled.ToggleButton onClick={toggleMenu} isOpen={isOpen}></Styled.ToggleButton>
        <Styled.StudyPlan>
          <Styled.StudyBox>
            <Styled.StudyTitle>Today's Plans</Styled.StudyTitle>
            <Styled.StudySpace>
              {timeTableDataList.length > 0? timeTableDataList.map((item, index) => (
                <Styled.PlanBox key={index}>
                  <span>{item.title}</span>
                </Styled.PlanBox>
              )): "No plans"}
            </Styled.StudySpace>
          </Styled.StudyBox>
          <Styled.StudyBox>
            <Styled.StudyTitle>Tomorrow Plans</Styled.StudyTitle>
            <Styled.StudySpace>
              {tomorrowDataList.length > 0 ? tomorrowDataList.map((item, index) => (
                <Styled.PlanBox key={index}>
                  <span>{item.title}</span>
                </Styled.PlanBox>
              )): "No plans"}
            </Styled.StudySpace>
          </Styled.StudyBox>
        </Styled.StudyPlan>
      </Styled.MenuContainer>
    </>
  );
};

export default SlideInMenu;

import { useEffect, useRef, useState } from "react";
import {
  ButtonAddSubject,
  ButtonInPlansPage,
  DivNotSubject,
  DivSubject,
  DivSubjectContainer,
  DivDayIndex,
  DivTimeCountInput,
  DivTimeCountInputContainer,
  DivTimetable,
  DivTimetableList,
  DivUserPlansContainer,
  DivUserPlansContent,
} from "./UserPlansStyle";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import planIcon from "../../assets/img/planIcon.png";
import { color1 } from "../../constants/colors";
import useGetTargetTime from "../../apis/member/useGetTargetTime";
import useGetPlanner from "../../apis/member/useGetPlanner";
import {
  FetchedPlannerData,
  SubjectListByDay,
} from "../../interfaces/member/UserPlannerData";
import usePostTargetTime from "../../apis/member/usePostTargetTime";
import usePostPlanner from "../../apis/member/usePostPlanner";
import { useParams } from "react-router-dom";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";

const defaultData: SubjectListByDay[] = [
  {
    index: 0,
    plan_detail: [],
  },
  {
    index: 1,
    plan_detail: [],
  },
  {
    index: 2,
    plan_detail: [],
  },
  {
    index: 3,
    plan_detail: [],
  },
  {
    index: 4,
    plan_detail: [],
  },
  {
    index: 5,
    plan_detail: [],
  },
  {
    index: 6,
    plan_detail: [],
  },
];

const UserPlans = () => {
  const [targetTime, setTargetTime] = useState<number>(8);
  const [timeTableDataList, setTimeTableDataList] = useState(defaultData);
  const [totalTimePerDayList, setTotalTimePerDayList] = useState(
    new Array(7).fill(0)
  );
  const dayList = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  const { memberId } = useParams();
  const loggedInMemberId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  const getTargetTime = useGetTargetTime();
  const postTargetTime = usePostTargetTime();
  const getPlanner = useGetPlanner();
  const postPlanner = usePostPlanner();

  useEffect(() => {
    const fetchData = async () => {
      if (memberId === undefined) return;
      const newTargetTime: number = await getTargetTime(+memberId);
      const fetchTimeTableDataList: FetchedPlannerData[] = await getPlanner(
        +memberId,
        {
          week: 0,
        }
      );

      const newTimeTableDataList = [...timeTableDataList];
      const newTotalTimePerDayList = [...totalTimePerDayList];
      fetchTimeTableDataList.forEach((data: FetchedPlannerData, idx) => {
        if (data) {
          newTimeTableDataList[idx].plan_detail = data.planDetails;
          const sum = data.planDetails.reduce(
            (acc, current) => acc + current.time,
            0
          );
          newTotalTimePerDayList[idx] = sum;
        }
      });
      setTargetTime(newTargetTime);
      setTimeTableDataList(newTimeTableDataList);
      setTotalTimePerDayList(newTotalTimePerDayList);
    };

    fetchData();
  }, []);

  const handleModifyTargetTime = (action: string) => {
    let tempValue = targetTime;
    if (action === "-") {
      tempValue--;
    } else if (action === "+") {
      if (tempValue === 24) {
        Swal.fire({
          icon: "error",
          width: "600px",
          title: `계획 시간은 24시간을 초과할 수 없습니다.`,
          confirmButtonColor: color1,
          confirmButtonText: "확인",
        });
        return;
      }
      tempValue++;
    }
    for (const day of timeTableDataList) {
      if (totalTimePerDayList[day.index] > tempValue) {
        Swal.fire({
          icon: "error",
          width: "600px",
          title: `계획 시간보다 목표 시간을 
          적게할 수 없습니다.`,
          confirmButtonColor: color1,
          confirmButtonText: "확인",
        });
        return;
      }
    }
    setTargetTime(tempValue);
  };

  // 계획 저장
  const handleSavePlanner = async () => {
    const today = new Date();
    const tempBody = {
      planners: [...timeTableDataList],
      today: today.toISOString().split("T")[0],
    };

    await postTargetTime({ target_time: targetTime });
    await postPlanner(tempBody);
  };

  const handleSwalInput = () => {
    const swalInput = document.getElementById("swal-input") as HTMLInputElement;
    const swalInput2 = document.getElementById(
      "swal2-input"
    ) as HTMLInputElement;
    return [swalInput.value, +swalInput2.value];
  };

  const handleAddSubjectClick = (selectedDayIdx: number) => {
    const newTimeTableDataList = [...timeTableDataList];
    const targetData: SubjectListByDay = newTimeTableDataList[selectedDayIdx];

    if (!targetData) return;

    if (totalTimePerDayList[targetData.index] >= targetTime) {
      Swal.fire({
        icon: "error",
        width: "600px",
        title: "남은 시간이 없습니다",
        confirmButtonColor: color1,
        confirmButtonText: "확인",
      });
      return;
    }

    (async () => {
      await Swal.fire({
        imageUrl: `${planIcon}`,
        imageWidth: "200px",
        imageHeight: "200px",
        width: "600px",
        title: `${dayList[targetData.index]}에 
        계획을 추가하시겠습니까?`,
        html: `
        <label forHtml="subjectName" class="swal2-input-label">과목명</label>
        <input type="text" id="swal-input" name="subjectNmae" class="swal2-input"/>
        `,
        input: "number",
        inputLabel: "목표 시간",
        inputValue: 1,
        inputAttributes: {
          min: "1",
          max: `${targetTime - totalTimePerDayList[targetData.index]}`,
        },
        customClass: {
          input: "swal2-input",
        },
        focusConfirm: false,
        confirmButtonColor: color1,
        confirmButtonText: "추가",
        showCancelButton: true,
        cancelButtonText: "취소",
        didOpen: () => {
          // 모달이 열릴 때 input 요소에 포커스를 줌
          const swalInput = document.getElementById(
            "swal-input"
          ) as HTMLInputElement;
          if (swalInput) {
            swalInput.focus();
          }
        },
        preConfirm: () => handleSwalInput(),
      }).then((result) => {
        // console.log(result.value);
        if (result.value) {
          targetData.plan_detail.push({
            title: result.value[0],
            time: result.value[1],
          });
          totalTimePerDayList[targetData.index] += result.value[1];
          newTimeTableDataList[selectedDayIdx] = targetData;
          setTimeTableDataList(newTimeTableDataList);
        }
      });
    })();
  };

  // 과목 div를 클릭했을 때,
  const handleSubjectClick = (selectedDayIdx: number, subjectIdx: number) => {
    const newTimeTableDataList = [...timeTableDataList];
    const targetData: SubjectListByDay = newTimeTableDataList[selectedDayIdx];
    const targetSubjectTimeCount = targetData.plan_detail[subjectIdx].time;
    // console.log(targetSubjectTimeCount);
    if (!targetData) return;

    (async () => {
      await Swal.fire({
        imageUrl: `${planIcon}`,
        imageWidth: "200px",
        imageHeight: "200px",
        width: "600px",
        title: `${targetData.plan_detail[subjectIdx].title} 과목을 
        변경하시겠습니까?`,
        html: `
        <label forHtml="subjectName" class="swal2-input-label">과목명</label>
        <input type="text" id="swal-input" name="subjectNmae" class="swal2-input" />
        `,
        input: "number",
        inputLabel: "목표 시간",
        inputValue: 1,
        inputAttributes: {
          min: "0",
          max: `${
            targetTime -
            totalTimePerDayList[targetData.index] +
            targetData.plan_detail[subjectIdx].time
          }`,
        },
        focusConfirm: false,
        confirmButtonColor: color1,
        confirmButtonText: "수정",
        showDenyButton: true,
        denyButtonText: "삭제",
        showCancelButton: true,
        cancelButtonText: "취소",
        didOpen: () => {
          // 모달이 열릴 때 input 요소에 포커스를 줌
          const swalInput = document.getElementById(
            "swal-input"
          ) as HTMLInputElement;
          if (swalInput) {
            swalInput.focus();
          }
        },
        preConfirm: () => handleSwalInput(),
      }).then((result) => {
        if (result.isConfirmed) {
          if (result.value) {
            targetData.plan_detail[subjectIdx] = {
              title: result.value[0],
              time: result.value[1],
            };
            totalTimePerDayList[targetData.index] +=
              result.value[1] - targetSubjectTimeCount;
            newTimeTableDataList[selectedDayIdx] = targetData;
            setTimeTableDataList(newTimeTableDataList);
          }
        } else if (result.isDenied) {
          targetData.plan_detail.splice(subjectIdx, 1);
          totalTimePerDayList[targetData.index] -= targetSubjectTimeCount;
          newTimeTableDataList[selectedDayIdx] = targetData;
          setTimeTableDataList(newTimeTableDataList);
        }
      });
    })();
  };

  // 드래그 앤 드롭 직접 구현
  const dragItem = useRef<number[]>();
  const dragOverItem = useRef<number[]>();

  const dragStart = (dayIdx: number, subjectIdx: number) => {
    dragItem.current = [dayIdx, subjectIdx];
  };

  const dragEnter = (dayIdx: number, subjectIdx: number) => {
    dragOverItem.current = [dayIdx, subjectIdx];
    // console.log(dayIdx, subjectIdx);
  };

  const drop = () => {
    if (dragItem.current === undefined || dragOverItem.current === undefined)
      return;
    const newTimeTableDataList = [...timeTableDataList];
    const dataForDelete = newTimeTableDataList[dragItem.current[0]];
    const dragItemValue = dataForDelete.plan_detail[dragItem.current[1]];
    const dataForAdd = newTimeTableDataList[dragOverItem.current[0]];

    if (
      dragItem.current[0] !== dragOverItem.current[0] &&
      dragItemValue.time + totalTimePerDayList[dataForAdd.index] > targetTime
    ) {
      Swal.fire({
        icon: "error",
        width: "600px",
        title: "목표 시간을 초과합니다.",
        confirmButtonColor: color1,
        confirmButtonText: "확인",
      });
      return;
    }

    dataForDelete.plan_detail.splice(dragItem.current[1], 1);

    if (dragOverItem.current[1] === -1) {
      dataForAdd.plan_detail.push(dragItemValue);
    } else {
      dataForAdd.plan_detail.splice(dragOverItem.current[1], 0, dragItemValue);
    }

    if (dragItem.current[0] !== dragOverItem.current[0]) {
      totalTimePerDayList[dataForDelete.index] -= dragItemValue.time;
      totalTimePerDayList[dataForAdd.index] += dragItemValue.time;
    }

    dragItem.current = undefined;
    dragOverItem.current = undefined;

    setTimeTableDataList(newTimeTableDataList);
  };

  return (
    <DivUserPlansContainer>
      <DivUserPlansContent>
        <DivTimeCountInputContainer>
          <ButtonInPlansPage
            width={"40px"}
            onClick={() => handleModifyTargetTime("-")}
          >
            -
          </ButtonInPlansPage>
          <DivTimeCountInput>
            <label htmlFor="targetCount">
              <span>일일 목표 시간</span>
            </label>
            <input
              type="text"
              className="input-time-count"
              value={targetTime + "시간"}
              id="targetCount"
              disabled
            />
          </DivTimeCountInput>
          <ButtonInPlansPage
            width={"40px"}
            onClick={() => handleModifyTargetTime("+")}
          >
            +
          </ButtonInPlansPage>
          {memberId && +memberId === loggedInMemberId ? (
            <ButtonInPlansPage
              className="right"
              width={"200px"}
              onClick={handleSavePlanner}
            >
              계획 저장
            </ButtonInPlansPage>
          ) : null}
        </DivTimeCountInputContainer>
        <DivTimetableList>
          {timeTableDataList.map((data, idx) => (
            <DivTimetable key={idx}>
              <DivDayIndex>{dayList[data.index]}</DivDayIndex>
              <DivSubjectContainer timeCount={targetTime}>
                {data.plan_detail.map((subject, idx2) => (
                  <DivSubject
                    key={idx2}
                    timeCount={subject.time}
                    onClick={() => handleSubjectClick(idx, idx2)}
                    draggable
                    onDragStart={() => dragStart(idx, idx2)}
                    onDragEnter={() => dragEnter(idx, idx2)}
                    onDragEnd={drop}
                    onDragOver={(e) => e.preventDefault()}
                  >
                    <div className="title">{subject.title}</div>
                  </DivSubject>
                ))}
                <DivNotSubject
                  timeCount={targetTime - totalTimePerDayList[data.index]}
                  onDragEnter={() => dragEnter(idx, -1)}
                  onDragOver={(e) => e.preventDefault()}
                />
              </DivSubjectContainer>
              <ButtonAddSubject onClick={() => handleAddSubjectClick(idx)}>
                +
              </ButtonAddSubject>
            </DivTimetable>
          ))}
        </DivTimetableList>
      </DivUserPlansContent>
    </DivUserPlansContainer>
  );
};

export default UserPlans;

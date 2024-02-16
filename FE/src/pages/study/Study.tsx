import { Viewer } from "@toast-ui/react-editor";
import { debounce } from "lodash";
import { ReactNode, useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import useDeleteTempFeed from "../../apis/tempFeed/useDeleteTempFeed.tsx";
import usePostTempFeed from "../../apis/tempFeed/usePostTempFeed.tsx";
import home from "../../assets/icon/home.png";
import Dropdown from "../../components/common/Dropdown.tsx";
import CodeEditor from "../../components/study/CodeEditor.tsx";
import Github from "../../components/study/Github.tsx";
import Markdown from "../../components/study/Markdown.tsx";
import Stopwatch from "../../components/study/Stopwatch.tsx";
import StudySlide from "../../components/study/StudySlide.tsx";
import { useAppDispatch, useAppSelector } from "../../store/hooks.tsx";
import { RootState } from "../../store/store.tsx";

import {
  deleteInstaceData,
  initializeTotalTime,
  pushTimeLogs,
  setInstanceDataByDrag,
  setRunning,
} from "../../store/tempFeedSlice.tsx";
import {
  InstanceAdder,
  InstanceBox,
  StudyCancle,
  StudyConfirm,
  StudyContainer,
  StudyFooter,
  StudyFooterBtns,
  StudyHeader,
  StudyInput,
  StudyOutput,
  ToHome,
} from "./StudyStyle.tsx";

const Study = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const postTempFeed = usePostTempFeed();
  const deleteTempFeed = useDeleteTempFeed();
  const [isLoad, setIsLoad] = useState(false);
  const timeLogs = useAppSelector(
    (state: RootState) => state.tempFeed.data.timelogs
  );
  const running = useAppSelector((state: RootState) => state.tempFeed.running);

  let id = 0;
  const [instances, setInstances] = useState<[number, ReactNode][]>([]);
  const instanceData = useAppSelector(
    (state: RootState) => state.tempFeed.data.instanceData
  );

  // 인스턴스 제거 로직
  const deleteData = (id: number) => {
    setInstances((prev) => {
      const newInstances = prev.filter((instance) => instance[0] !== id);
      return newInstances;
    });
    dispatch(deleteInstaceData({ id }));
  };

  // 인스턴스 추가 로직
  const addMarkdown = () => {
    setInstances((prev) => [
      ...prev,
      [id, <Markdown id={id++} deleteData={deleteData} />],
    ]);
  };
  const addCodeEditor = () => {
    setInstances((prev) => [
      ...prev,
      [id, <CodeEditor id={id++} deleteData={deleteData} />],
    ]);
  };
  const addGithub = () => {
    setInstances((prev) => [
      ...prev,
      [id, <Github id={id++} deleteData={deleteData} />],
    ]);
  };

  // 드롭다운 로직
  const [state, setState] = useState({
    position: {
      bottom: "50px",
      left: "00px",
    },
    size: {
      width: 300,
      height: 150,
    },
    buttons: [
      {
        useFunction: () => {
          addMarkdown();
        },
        content: "Markdown",
      },
      {
        useFunction: () => {
          addCodeEditor();
        },
        content: "Code Editor",
      },
      {
        useFunction: () => {
          addGithub();
        },
        content: "Github Commit",
      },
    ],
    isActive: false,
  });
  const children = <InstanceAdder>+</InstanceAdder>;

  // 뷰어 로직
  const viewerRef = useRef<Viewer>(null);
  const [viewerContent, setViewerContent] = useState<string>("");
  useEffect(() => {
    // 뷰어 콘턴츠 변경
    const arrayFormData: [number, [string, string]][] =
      JSON.parse(instanceData);
    const mapFormJSON = new Map<number, [string, string]>(arrayFormData);
    const contents = Array.from(mapFormJSON.values())
      .map((value) => value[1])
      .join("\n\n");

    setViewerContent(contents);
  }, [instanceData]);
  useEffect(() => {
    // 뷰어에 콘텐츠 적용
    viewerRef.current?.getInstance().setMarkdown(viewerContent);
  }, [viewerContent]);

  // 불러오기 로직
  useEffect(() => {
    if (isLoad) return;
    setIsLoad(true);
    if (instanceData === "[]") return;
    const arrayFormData: [number, [string, string]][] =
      JSON.parse(instanceData);
    arrayFormData.map((data) => {
      const dataType = data[1][0];
      const rawData = data[1][1];

      if (dataType === "markdown") {
        setInstances((prev) => [
          ...prev,
          [
            id,
            <Markdown
              id={id++}
              deleteData={deleteData}
              initialData={rawData}
            />,
          ],
        ]);
      } else if (dataType === "codeEditor") {
        const regex = /```(\w+)\n([\s\S]+)\n```/;
        const match = rawData.match(regex);

        if (!match) return;
        const language = match[1];
        const content = match[2];
        const initialData = { language, content };
        setInstances((prev) => [
          ...prev,
          [
            id,
            <CodeEditor
              id={id++}
              deleteData={deleteData}
              initialData={initialData}
            />,
          ],
        ]);
      } else if (dataType === "github") {
        // 깃허브는 미구현
        setInstances((prev) => [
          ...prev,
          [id, <Github id={id++} deleteData={deleteData} />],
        ]);
      }
    });
  }, [instanceData]);

  // 드래그 앤 드롭 구현
  const dragItem = useRef<number>();
  const dragOverItem = useRef<number>();
  const [dragging, setDragging] = useState(false);
  const dragStart = (idx: number) => {
    dragItem.current = idx;
    setDragging(true);
    // console.log(idx);
  };
  const dragEnter = (idx: number) => {
    dragOverItem.current = idx;
    // console.log(idx);
  };
  const drop = () => {
    setDragging(false);
    if (dragItem.current === undefined || dragOverItem.current === undefined)
      return;

    const newInstances = [...instances];
    const dragItemValue = newInstances[dragItem.current];

    // 배열에서 삭제
    newInstances.splice(dragItem.current, 1);
    // 원하는 인덱스에 다시 추가
    newInstances.splice(dragOverItem.current, 0, dragItemValue);

    const arrayFromInstanceData = JSON.parse(instanceData);
    const dragInstanceDataValue = arrayFromInstanceData[dragItem.current];

    arrayFromInstanceData.splice(dragItem.current, 1);
    arrayFromInstanceData.splice(
      dragOverItem.current,
      0,
      dragInstanceDataValue
    );

    dragItem.current = undefined;
    dragOverItem.current = undefined;

    setInstances(newInstances);
    dispatch(
      setInstanceDataByDrag(
        JSON.stringify(Array.from(new Map(arrayFromInstanceData)))
      )
    );
  };

  // 취소, 저장 버튼 로직
  const handleToHome = () => {
    if (!running) return;
    navigate("/");
    dispatch(setRunning(false));
    dispatch(pushTimeLogs());
    postTempFeed();
  };
  const handleCancel = () => {
    if (!running) return;
    if (confirm("임시저장 피드를 삭제하시겠습니까?")) {
      navigate("/");
      dispatch(setRunning(false));
      deleteTempFeed();
    }
  };
  const handleConfirm = () => {
    if (instanceData === "[]") {
      alert("빈 게시물은 저장할 수 없습니다.");
      return;
    }
    if (!running) return;
    navigate("/study/upload");
    dispatch(setRunning(false));
    dispatch(pushTimeLogs());
    postTempFeed();
  };

  // 임시 저장 디바운스 구현
  const debouncePostTempFeed = debounce(() => {
    postTempFeed();
  }, 10000);
  useEffect(() => {
    if (instanceData !== "[]") return;
    debouncePostTempFeed();
  }, [instanceData, timeLogs]);

  // 총 공부시간 초기화 로직
  useEffect(() => {
    dispatch(setRunning(false));
    if (instanceData !== "[]") return;
    dispatch(initializeTotalTime());
  }, []);

  return (
    <StudyContainer>
      <StudySlide />
      <StudyHeader>
        <Stopwatch />
        <ToHome onClick={handleToHome}>
          <img src={home} alt="홈으로" width="24px" />
        </ToHome>
      </StudyHeader>
      <StudyInput>
        {instances &&
          instances.map((instance, idx) => (
            <InstanceBox
              key={instance[0]}
              draggable
              className={dragging ? "dragging" : undefined}
              onDragStart={() => dragStart(idx)}
              onDragEnter={() => dragEnter(idx)}
              onDragEnd={drop}
              onDragOver={(e) => e.preventDefault()}
            >
              {instance[1]}
            </InstanceBox>
          ))}
      </StudyInput>
      <StudyOutput>
        <Viewer ref={viewerRef} initialValue={viewerContent} />
      </StudyOutput>
      <StudyFooter>
        <Dropdown state={state} setState={setState} children={children} />
        <StudyFooterBtns>
          <StudyCancle onClick={handleCancel}>취소</StudyCancle>
          <StudyConfirm onClick={handleConfirm}>저장</StudyConfirm>
        </StudyFooterBtns>
      </StudyFooter>
    </StudyContainer>
  );
};

export default Study;

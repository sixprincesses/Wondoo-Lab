import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import usePostTempFeed from "../../apis/tempFeed/usePostTempFeed";
import { color1, color3 } from "../../constants/colors";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";
import {
  pushTimeLogs,
  setRunning,
  setStartTime,
} from "../../store/tempFeedSlice";

interface StartModalProps {
  onClose: (confirmed: boolean) => void;
}

const StartModal = ({ onClose }: StartModalProps) => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const postTempFeed = usePostTempFeed();
  const instanceData = useAppSelector(
    (state: RootState) => state.tempFeed.data.instanceData
  );

  const showAlert = () => {
    Swal.fire({
      title: "공부를 시작하시겠습니까?",
      icon: "warning",
      confirmButtonColor: `${color3}`,
      confirmButtonText: "홈으로",
      showDenyButton: true,
      denyButtonColor: `${color1}`,
      denyButtonText: "공부 시작하기",
      showCancelButton: true,
      cancelButtonColor: "wheat",
      cancelButtonText: "저장",
      allowOutsideClick: false,
      allowEscapeKey: false,
    }).then((result) => {
      if (result.isConfirmed) {
        navigate("/");
        dispatch(setRunning(false));
        dispatch(pushTimeLogs());
        postTempFeed();
      } else if (result.isDenied) {
        dispatch(setRunning(true));
        dispatch(setStartTime());
        onClose(true);
      } else if (result.isDismissed) {
        if (instanceData === "[]") {
          dispatch(setRunning(true));
          dispatch(setStartTime());
          onClose(true);
          alert("빈 게시물은 저장할 수 없습니다.");
          return;
        }
        navigate("/study/upload");
        dispatch(setRunning(false));
        dispatch(pushTimeLogs());
        postTempFeed();
      } else {
        onClose(false);
      }
    });
  };

  useEffect(() => {
    showAlert();
    return () => {
      Swal.close();
    };
  }, [location]);

  return <div></div>;
};

export default StartModal;

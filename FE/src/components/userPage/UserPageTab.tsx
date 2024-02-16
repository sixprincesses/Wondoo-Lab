import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import { DivTab, DivTabListContainer } from "./UserPageTabStyle";
import { useAppSelector } from "../../store/hooks";
import { RootState } from "../../store/store";

const UserPageTabs = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { memberId } = useParams();
  const userId = useAppSelector(
    (state: RootState) => state.user.userInfo.member_id
  );

  const [nowTabName, setNowTabName] = useState<string | null>(
    memberId ? location.pathname.slice(memberId?.length + 9) : ""
  );

  if (
    memberId &&
    nowTabName !== location.pathname.slice(memberId?.length + 9)
  ) {
    setNowTabName(location.pathname.slice(memberId.length + 9));
  }

  const handleClick = (selectedTabName: string) => {
    navigate(`/member/${memberId}/${selectedTabName}`);
    setNowTabName(selectedTabName);
  };

  return (
    <DivTabListContainer>
      <DivTab
        className={nowTabName === "" ? "selected" : undefined}
        onClick={() => handleClick("")}
      >
        Stats
      </DivTab>
      <DivTab
        className={nowTabName === "feeds" ? "selected" : undefined}
        onClick={() => handleClick("feeds")}
      >
        Feeds
      </DivTab>

      <DivTab
        className={nowTabName === "diary" ? "selected" : undefined}
        onClick={() => handleClick("diary")}
      >
        Diary
      </DivTab>
      <DivTab
        className={nowTabName === "plans" ? "selected" : undefined}
        onClick={() => handleClick("plans")}
      >
        Plans
      </DivTab>
      {memberId && +memberId === userId ? (
        <DivTab
          className={nowTabName === "bookmark" ? "selected" : undefined}
          onClick={() => handleClick("bookmark")}
        >
          Bookmark
        </DivTab>
      ) : (
        <DivTab
          className={nowTabName === "similarity" ? "selected" : undefined}
          onClick={() => handleClick("similarity")}
        >
          Similarity
        </DivTab>
      )}
    </DivTabListContainer>
  );
};

export default UserPageTabs;

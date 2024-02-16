import { useEffect } from "react";
import useGetKeywordRanks from "../../apis/keyword/useGetKeywordRanks";
import useGetRank from "../../apis/member/useGetRank";
import SidebarKeyword from "./SidebarKeyword";
import { Wrapper } from "./SidebarMainStyle";
import SidebarTimer from "./SidebarTimer";
import SidebarUserRank from "./SidebarUserRank";
// import useFeedLast from "../../apis/feed/useFeedLast"

const SidebarMain = () => {
  const getRank = useGetRank();
  const getKeywordRanks = useGetKeywordRanks();
  const access_token = localStorage.getItem("accessToken");
  // const lastfeed = useFeedLast();


  // const [isAvailable, setIsAvailable] = useState(false);

  // useEffect(() => {

  //   if (access_token) {
  //     const fetchLastFeed = async () => {
  //       try {
  //         const lastFeedData = await lastfeed();
  //         console.log(lastFeedData)
  //         if (lastFeedData.date instanceof Date) { 
  //           setIsAvailable(true);
  //         }
  //       } catch (error) {
  //         console.error("Error fetching last feed: ", error);
  //         setIsAvailable(false);
  //       }
  //     };

  //     fetchLastFeed();
  //   }
  // }, [isAvailable]);

  useEffect(() => {
    getRank();
    getKeywordRanks();
  }, []);

  return (
    <Wrapper>
      <SidebarUserRank />
      <SidebarKeyword />
      {access_token ? <SidebarTimer /> : null}
    </Wrapper>
  );
};

export default SidebarMain;

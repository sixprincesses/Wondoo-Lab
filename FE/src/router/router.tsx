import { createBrowserRouter } from "react-router-dom";

import ChatTab from "../components/chat/ChatTab";
import Header from "../components/common/Header";
import FollowTab from "../components/follow/FollowTab";
import Followers from "../components/follow/Followers";
import Followings from "../components/follow/Followings";
import Layout from "../components/layout/Layout";
import LayoutChat from "../components/layout/LayoutChat";
import LayoutMain from "../components/layout/LayoutMain";
import LayoutMyPage from "../components/layout/LayoutSetting";
import LayoutStudy from "../components/layout/LayoutStudy";
import SidebarMain from "../components/main/SidebarMain";
import ProtectedRoute from "../components/routeProtect/ProtectedRoute";
import SidebarSetting from "../components/setting/SidebarSetting";
import Callback from "../components/signup/Callback";
import UserBookmarks from "../components/userPage/UserBookmarks";
import UserDiary from "../components/userPage/UserDiary";
import UserFeeds from "../components/userPage/UserFeeds";
import UserPlans from "../components/userPage/UserPlans";
import UserStats from "../components/userPage/UserStats";
import Signup from "../pages/Signup";
import Chat from "../pages/chat/Chat";
import Follow from "../pages/follow/Follow";
import Detail from "../pages/main/Detail";
import Home from "../pages/main/Home";
import SearchFeeds from "../pages/search/SearchFeeds";
import Alarm from "../pages/setting/Alarm";
import Theme from "../pages/setting/Theme";
import UserInfo from "../pages/setting/UserInfo";
import Study from "../pages/study/Study";
import Upload from "../pages/study/Upload";
import UserPage from "../pages/userPage/UserPage";
import UserSimilarity from "../components/userPage/UserSimilarity";
import NotFound from "../pages/notFound/NotFound";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <Layout>
        <Header />
      </Layout>
    ),
    children: [
      {
        path: "",
        element: (
          <LayoutMain>
            <SidebarMain />
          </LayoutMain>
        ),
        children: [
          {
            path: "",
            element: <Home />,
          },
          {
            path: "/detail/:feedId",
            element: <Detail />,
          },
        ],
      },
      {
        path: "/search/:keyword",
        element: <SearchFeeds />,
      },
      {
        path: "/chat",
        element: (
          <ProtectedRoute>
            <LayoutChat>
              <ChatTab />
            </LayoutChat>
          </ProtectedRoute>
        ),
        children: [
          {
            path: "",
            element: <Chat />,
          },
          {
            path: "/chat/:channelRef",
            element: <Chat />,
          },
        ],
      },
      {
        path: "/member/:memberId",
        element: (
          <ProtectedRoute>
            <UserPage />
          </ProtectedRoute>
        ),
        children: [
          {
            path: "",
            element: <UserStats />,
          },
          {
            path: "/member/:memberId/diary",
            element: <UserDiary />,
          },
          {
            path: "/member/:memberId/plans",
            element: <UserPlans />,
          },
          {
            path: "/member/:memberId/feeds",
            element: <UserFeeds />,
          },
          {
            path: "/member/:memberId/bookmark",
            element: <UserBookmarks />,
          },
          {
            path: "/member/:memberId/similarity",
            element: <UserSimilarity />,
          },
        ],
      },
      {
        path: "/follow/:memberId",
        element: (
          <ProtectedRoute>
            <Follow>
              <FollowTab />
            </Follow>
          </ProtectedRoute>
        ),
        children: [
          {
            path: "/follow/:memberId/followers",
            element: <Followers />,
          },
          {
            path: "/follow/:memberId/followings",
            element: <Followings />,
          },
        ],
      },
      {
        path: "/setting",
        element: (
          <ProtectedRoute>
            <LayoutMyPage>
              <SidebarSetting />
            </LayoutMyPage>
          </ProtectedRoute>
        ),
        children: [
          {
            path: "",
            element: <UserInfo />,
          },
          {
            path: "/setting/alarm",
            element: <Alarm />,
          },
          {
            path: "/setting/theme",
            element: <Theme />,
          },
        ],
      },
    ],
  },
  {
    path: "/study",
    element: (
      <ProtectedRoute>
        <LayoutStudy />
      </ProtectedRoute>
    ),
    children: [
      {
        path: "",
        element: <Study />,
      },
      {
        path: "/study/upload",
        element: <Upload />,
      },
    ],
  },
  {
    path: "/signup",
    element: <Signup />,
  },
  {
    path: "/callback",
    element: <Callback />,
  },
  {
    path: "*",
    element: (
      <Layout>
        <Header />
        <NotFound />
      </Layout>
    ),
  },
]);

export { router };

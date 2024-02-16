import { RouterProvider } from "react-router-dom";
import { router } from "./router/router";
import { AppWrapper } from "./AppStyle";
import { Suspense } from "react";
import Loading from "./pages/loading/Loading";

function App() {
  return (
    <AppWrapper>
      <Suspense fallback={<Loading />}>
        <RouterProvider router={router} />
      </Suspense>
    </AppWrapper>
  );
}

export default App;

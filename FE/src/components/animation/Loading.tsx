import Lottie from "lottie-react";

import Loading from "../../assets/lottie/Loading.json";

const style = {
  width: "500px",
};

function LoadingLottie() {
  return <Lottie animationData={Loading} style={style} />;
}

export default LoadingLottie;

import Lottie from "lottie-react";

import NotFount from "../../assets/lottie/NotFound.json";

const style = {
  width: "600px",
  height: "500px",
  marginTop: "-300px",
};

function NotFoundLottie() {
  return <Lottie animationData={NotFount} style={style} />;
}

export default NotFoundLottie;

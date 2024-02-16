// import { useEffect } from 'react';
// import Swal from 'sweetalert2';
// import 'sweetalert2/dist/sweetalert2.min.css';
// import { color1 } from '../../constants/colors';

// const ServiceTerm = () => {
//     const today = new Date().toLocaleDateString();

//     const showAlert = () => {
//         Swal.fire({
//         title: 'Wondoo 이용약관',
//         html: `
//             <div style="font-family: Arial, sans-serif; font-size: 14px;">
//             <p><strong>효력 발생일:</strong> ${today}</p>
//             <br>
//             <p>환영합니다. Wondoo를 이용해 주셔서 감사합니다. 본 이용약관은 Wondoo 서비스의 이용 조건을 규정합니다. 본 웹사이트를 이용함으로써 본 약관에 동의하게 됩니다. 아래의 내용을 주의 깊게 읽어주시기 바랍니다.</p>
//             <ol>
//                 <li>
//                 <br>
//                 <h2>서비스 내용</h2><br>
//                 <ul>
//                     <li>1.1 Wondoo는 커피 및 관련 상품 및 서비스를 온라인으로 제공하는 플랫폼입니다.</li>
//                     <li>1.2 본 서비스는 회원 가입 후에만 이용 가능합니다.</li>
//                     <li>1.3 Wondoo는 회원이 제공한 정보를 기반으로 맞춤형 서비스를 제공할 수 있습니다.</li>
//                     <li>1.4 서비스 이용 중 발생한 모든 거래는 회원 본인의 책임입니다.</li>
//                 </ul>
//                 </li>
//                 <li>
//                 <br>
//                 <h2>이용자의 의무</h2><br>
//                 <ul>
//                     <li>2.1 이용자는 본 서비스를 이용함에 있어서 관련 법령 및 본 약관에 준수하여야 합니다.</li>
//                     <li>2.2 이용자는 본 서비스를 부정하게 이용하여서는 안 됩니다.</li>
//                     <li>2.3 이용자는 개인정보를 보호하고, 타인의 개인정보를 침해해서는 안 됩니다.</li>
//                 </ul>
//                 </li>
//                 <!-- Add more sections as needed -->
//             </ol>
//             <p style="margin-top: 20px;"> 이용약관에 동의하려면 '동의하기'를 클릭하세요.</p>
//             <br><br>
//             <p><h2>문의하기</h2><br>이용약관에 관련하여 문의사항이 있으시면 아래 연락처로 문의해주세요.</p>
//             <br><p>이메일: help@wondoo.kr</p><br>
//             <p><strong>최종 수정일:</strong> ${today}</p>
//             </div>
//         `,
//         showCancelButton: false,
//         confirmButtonColor: `${color1}`,
//         confirmButtonText: "동의하기",
//         allowOutsideClick: false,
//         }).then(() => {
//         });
//     };

//   useEffect(() => {
//     showAlert();
//     return () => {
//       Swal.close();
//     };
//   }, []);

//   return <div></div>;
// };

// export default ServiceTerm;
import { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle`
    // 폰트 
    @font-face {
        font-family: "Pretendard-Regular";
        src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff") format("woff");
        font-weight: 400;
        font-style: normal;
    }  

    * {
        font-family: Pretendard-Regular, "Arial", sans-serif;
        font-size: 16px;
    }
`;

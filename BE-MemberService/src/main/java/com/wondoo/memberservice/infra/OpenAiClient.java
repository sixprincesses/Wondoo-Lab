package com.wondoo.memberservice.infra;

public interface OpenAiClient {

    /**
     * "OPENAI"의 연결을 확인합니다. <p>
     * 200 : 정상적으로 연결됩니다. <p>
     * 401 : 유효하지 않은 "API token" 입니다. <p>
     * 429 : 사용할 수 없는 "API token" 입니다. <p>
     * 500 : 서버의 오류입니다.
     *
     * @param token 사용자의 OPENAI API Key
     * @return HttpStatusCode
     */
    int getStatusCodeByConnection(String token);

    String diaryByPrompt(Long memberId, String token, String commits);
}

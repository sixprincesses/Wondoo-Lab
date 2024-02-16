package com.wondoo.storageservice._global.data;

import lombok.Getter;

/**
 * SUCCESS CODE
 * SELECT, INSERT, UPDATE, DELETE
 *
 * ERROR CODE
 * HTTP Status Code
 * 400 : Bad Request
 * 401 : Unauthorized
 * 403 : Forbidden
 * 404 : Not Found
 * 500 : Internal Server Error
 * https://developers.worksmobile.com/kr/docs/error-codes
 */

@Getter
public enum StatusCode {

    // ==================================================
    // SUCCESS CODE

    SUCCESS_SELECT(201, "SELECT SUCCESS"),
    SUCCESS_INSERT(201, "INSERT SUCCESS"),
    SUCCESS_UPDATE(204, "UPDATE SUCCESS"),
    SUCCESS_DELETE(204, "DELETE SUCCESS"),

    SUCCESS_TEST(205, "TEST SUCCESS"),

    // ==================================================
    // ERROR CODE

    // 400
    // BAD_REQUEST_ERROR                : 잘못된 서버 요청
    // MISSING_PARAMETER_ERROR          : 잘못된 파라미터
    ERROR_BAD_REQUEST(400, "BAD REQUEST ERROR"),
    ERROR_MISSING_PARAMETER(400, "MISSING PARAMETER ERROR"),

    // 401
    // UNAUTHORIZED_ERROR               : 인증 정보 누락
    ERROR_UNAUTHORIZED(401, "UNAUTHORIZED ERROR"),

    // 403
    // FORBIDDEN_ERROR                  : 접근 권한 제한
    ERROR_FORBIDDEN(403, "FORBIDDEN ERROR"),

    // 404
    // NOT_FOUND_ERROR                  : 리소스를 찾을 수 없음
    // NULL_POINT_EXCEPTION_ERROR       : Null Point Exception
    ERROR_NOT_FOUND(404, "NOT FOUND ERROR"),
    ERROR_NULL_POINT_EXCEPTION(404, "NULL POINT EXCEPTION ERROR"),

    // 409
    // ALREADY_EXIST_ERROR              : 리소스 이미 존재
    ERROR_ALREADY_EXIST(409, "ALREADY_EXIST_ERROR"),

    // 410
    // DELETED_ERROR                    : 리소스가 삭제되어 찾을 수 없음
    ERROR_DELETED(409, "DELETED ERROR")

    ;

    private final int status;
    private final String message;

    StatusCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}

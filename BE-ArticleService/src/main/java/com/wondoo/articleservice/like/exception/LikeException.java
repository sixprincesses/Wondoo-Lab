package com.wondoo.articleservice.like.exception;

import com.wondoo.articleservice._global.exception.ArticleException;
import com.wondoo.articleservice._global.exception.ErrorCode;

public class LikeException extends ArticleException {
    public LikeException(ErrorCode code) {
        super(code);
    }
}

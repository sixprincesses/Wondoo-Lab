package com.wondoo.articleservice.tempfeed.exception;


import com.wondoo.articleservice._global.exception.ArticleException;
import com.wondoo.articleservice._global.exception.ErrorCode;

public class TempFeedException extends ArticleException {
    public TempFeedException(ErrorCode code) {
        super(code);
    }
}

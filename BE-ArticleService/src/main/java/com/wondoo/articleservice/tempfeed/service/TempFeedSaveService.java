package com.wondoo.articleservice.tempfeed.service;

import com.wondoo.articleservice.tempfeed.data.request.TempFeedSaveRequest;

public interface TempFeedSaveService {

    void tempFeedSave(Long memberId, TempFeedSaveRequest tempFeedSaveRequest);

    void tempFeedDelete(Long memberId);
}

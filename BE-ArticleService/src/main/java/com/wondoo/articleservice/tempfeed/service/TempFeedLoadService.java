package com.wondoo.articleservice.tempfeed.service;

import com.wondoo.articleservice.tempfeed.data.response.TempFeedLoadResponse;

public interface TempFeedLoadService {

    TempFeedLoadResponse tempFeedLoad(Long memberId);
}

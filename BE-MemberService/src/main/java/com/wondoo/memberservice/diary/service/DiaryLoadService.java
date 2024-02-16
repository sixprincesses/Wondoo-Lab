package com.wondoo.memberservice.diary.service;

import com.wondoo.memberservice.diary.data.response.DiarySaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryLoadService {
    Page<DiarySaveResponse> diaryLoadAll(Long memberId, Pageable pageable);
}

package com.wondoo.memberservice.diary.service;

import com.wondoo.memberservice.diary.data.response.DiarySaveResponse;

import java.io.IOException;
import java.time.LocalDate;

public interface DiarySaveService {

    DiarySaveResponse diarySave(Long memberId, LocalDate localDate) throws IOException;

    void diarySaveAuto();

    void diaryDelete(Long memberId, String diaryId);
}

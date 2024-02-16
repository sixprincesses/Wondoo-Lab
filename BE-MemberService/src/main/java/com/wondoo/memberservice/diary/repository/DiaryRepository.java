package com.wondoo.memberservice.diary.repository;

import com.wondoo.memberservice.diary.data.response.DiarySaveResponse;
import com.wondoo.memberservice.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends MongoRepository<Diary, String> {
    Optional<Diary> findByMemberIdAndDate(Long memberId, LocalDate date);
    Page<DiarySaveResponse> findByMemberId(Long memberId, Pageable pageable);
}

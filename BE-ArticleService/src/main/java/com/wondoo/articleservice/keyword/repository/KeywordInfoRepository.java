package com.wondoo.articleservice.keyword.repository;

import com.wondoo.articleservice.keyword.domain.KeywordInfo;
import com.wondoo.articleservice.keyword.repository.query.KeywordInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordInfoRepository extends JpaRepository<KeywordInfo, Long>, KeywordInfoRepositoryCustom {
    void deleteAllByFeedId(Long feedId);
}

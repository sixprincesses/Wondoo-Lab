package com.wondoo.articleservice.keyword.repository;

import com.wondoo.articleservice.keyword.domain.KeywordRank;
import com.wondoo.articleservice.keyword.repository.query.KeywordRankRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRankRepository extends JpaRepository<KeywordRank, Long>, KeywordRankRepositoryCustom {
    Optional<KeywordRank> findByKeyword_Id(Long keywordId);
}

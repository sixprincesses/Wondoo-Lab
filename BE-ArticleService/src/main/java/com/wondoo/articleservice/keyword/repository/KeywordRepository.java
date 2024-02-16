package com.wondoo.articleservice.keyword.repository;

import com.wondoo.articleservice.keyword.domain.Keyword;
import com.wondoo.articleservice.keyword.repository.query.KeywordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long>, KeywordRepositoryCustom {
    Optional<Keyword> findByName(String name);
}

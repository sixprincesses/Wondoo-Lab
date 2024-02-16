package com.wondoo.articleservice.bookmark.repository;

import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.repository.query.BookmarkRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {
}

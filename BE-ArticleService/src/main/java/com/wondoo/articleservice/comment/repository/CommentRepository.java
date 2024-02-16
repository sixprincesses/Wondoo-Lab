package com.wondoo.articleservice.comment.repository;

import com.wondoo.articleservice.comment.domain.Comment;
import com.wondoo.articleservice.comment.repository.query.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findAllByFeedIdOrderByCreatedTimeDesc(Long feedId);
}

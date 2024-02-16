package com.wondoo.articleservice.like.repository.query;

import com.wondoo.articleservice.like.data.cond.FindLikeCond;
import com.wondoo.articleservice.like.data.cond.FindLikesCond;
import com.wondoo.articleservice.like.domain.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepositoryCustom {
    Optional<Like> findByLikeCond(FindLikeCond cond);

    List<Like> findLikesCond(FindLikesCond cond);
}

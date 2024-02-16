package com.wondoo.articleservice.like.utils;

import com.wondoo.articleservice.like.domain.Like;
import com.wondoo.articleservice.like.exception.LikeErrorCode;
import com.wondoo.articleservice.like.exception.LikeException;
import com.wondoo.articleservice.like.repository.LikeRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeServiceUtils {
    public static Like findByMemberIdAndFeedId(LikeRepository repository, Long memberId, Long feedId) {
        return repository.findLikeByMemberIdAndFeedId(memberId, feedId).orElseThrow(
                () -> new LikeException(LikeErrorCode.NON_EXISTED_LIKE)
        );
    }

    public static boolean isLikedByMemberIdAndFeedId(LikeRepository repository, Long memberId, Long feedId) {
        if (memberId == null || feedId == null) {
            return false;
        }
        return repository.findLikeByMemberIdAndFeedId(memberId, feedId).isPresent();
    }
}

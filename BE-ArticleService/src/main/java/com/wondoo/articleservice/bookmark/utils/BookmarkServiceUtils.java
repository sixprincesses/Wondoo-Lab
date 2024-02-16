package com.wondoo.articleservice.bookmark.utils;

import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.exception.BookmarkErrorCode;
import com.wondoo.articleservice.bookmark.exception.BookmarkException;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkServiceUtils {

    public static Bookmark findById(BookmarkRepository repository, Long id) {
        return repository.findById(id).orElseThrow(
                () -> new BookmarkException(BookmarkErrorCode.NON_EXISTED_BOOKMARK)
        );
    }

    public static Bookmark findByMemberIdAndFeedId(BookmarkRepository repository, Long memberId, Long feedId) {
        return repository.findBookmarkByMemberIdAndFeedId(memberId, feedId).orElseThrow(
                () -> new BookmarkException(BookmarkErrorCode.NON_EXISTED_BOOKMARK)
        );
    }

    public static boolean isBookmarkedByMemberIdAndFeedId(BookmarkRepository repository, Long memberId, Long feedId) {
        if (memberId == null || feedId == null) {
            return false;
        }
        return repository.findBookmarkByMemberIdAndFeedId(memberId, feedId).isPresent();
    }
}

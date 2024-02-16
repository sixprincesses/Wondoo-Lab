package com.wondoo.articleservice.bookmark.repository.query;

import com.wondoo.articleservice.bookmark.data.cond.FindBookmarkCond;
import com.wondoo.articleservice.bookmark.data.cond.FindBookmarksCond;
import com.wondoo.articleservice.bookmark.domain.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepositoryCustom {
    Optional<Bookmark> findBookmarkByMemberIdAndFeedId(Long memberId, Long feedId);

    Optional<Bookmark> findByBookmarkCond(FindBookmarkCond cond);

    List<Bookmark> findBookmarks(FindBookmarksCond cond);
}

package com.wondoo.articleservice.bookmark.repository.query;

import com.wondoo.articleservice.bookmark.data.cond.FindBookmarkCond;
import com.wondoo.articleservice.bookmark.data.cond.FindBookmarksCond;
import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.global.annotation.WondooDatabaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.wondoo.articleservice.global.fixture.BookmarkFixture.*;
import static com.wondoo.articleservice.global.fixture.FeedInfoFixture.FEED_INFO;
import static com.wondoo.articleservice.global.fixture.FeedInfoFixture.makeSavedFeedInfo;
import static org.assertj.core.api.Assertions.assertThat;

@WondooDatabaseTest
class BookmarkRepositoryCustomTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private FeedInfoRepository feedInfoRepository;

    private FeedInfo feedInfo;
    private Bookmark bookmark;

    @BeforeEach
    void setup() {
        feedInfo = feedInfoRepository.save(
                makeSavedFeedInfo(FEED_INFO)
        );
        bookmark = bookmarkRepository.save(
                makeBookmark(BOOKMARK_ID1, feedInfo.getId())
        );
    }

    @DisplayName("Find Bookmark Cond 테스트")
    @Nested
    class FindBookmarkCondTest {
        @DisplayName("조회에 성공한다")
        @Test
        void findBookmarkCondSuccess() {
            /* Given */
            FindBookmarkCond cond = FindBookmarkCond.of(
                    bookmark.getMemberId(),
                    feedInfo.getId()
            );

            /* When */
            Optional<Bookmark> findBookmark = bookmarkRepository.findByBookmarkCond(cond);

            /* Then */
            assertThat(findBookmark).isPresent();
        }
    }

    @DisplayName("Find Bookmarks 테스트")
    @Nested
    class findBookmarksTest {

        @BeforeEach
        void setup() {
            Bookmark bookmark2 = bookmarkRepository.save(
                    makeBookmark(BOOKMARK_ID2,
                            feedInfoRepository.save(
                                            makeSavedFeedInfo(FEED_INFO))
                                    .getId()
                    )
            );
            Bookmark bookmark3 = bookmarkRepository.save(
                    makeBookmark(BOOKMARK_ID3,
                            feedInfoRepository.save(
                                            makeSavedFeedInfo(FEED_INFO))
                                    .getId()
                    )
            );
        }

        @DisplayName("조회에 성공한다")
        @Test
        void findBookmarksSuccess() {
            /* Given */
            Long memberId = 1L;
            FindBookmarksCond cond = new FindBookmarksCond(memberId, null, 2, null);

            /* When */
            List<Bookmark> bookmarks = bookmarkRepository.findBookmarks(cond);

            /* Then */
            assertThat(bookmarks).hasSize(2);
        }
    }
}
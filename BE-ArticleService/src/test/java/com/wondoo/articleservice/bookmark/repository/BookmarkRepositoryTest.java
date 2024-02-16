package com.wondoo.articleservice.bookmark.repository;

import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.global.annotation.WondooDatabaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.wondoo.articleservice.global.fixture.BookmarkFixture.*;
import static com.wondoo.articleservice.global.fixture.FeedInfoFixture.FEED_INFO;
import static com.wondoo.articleservice.global.fixture.FeedInfoFixture.makeSavedFeedInfo;
import static org.assertj.core.api.Assertions.assertThat;

@WondooDatabaseTest
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private FeedInfoRepository feedInfoRepository;

    @DisplayName("Bookmark Save 테스트")
    @Nested
    class SaveTest {
        private FeedInfo feedInfo;

        @BeforeEach
        void setup() {
            feedInfo = feedInfoRepository.save(
                    makeSavedFeedInfo(FEED_INFO)
            );
        }

        @DisplayName("북마크 저장에 성공한다")
        @Test
        void saveSuccess() {
            /* Given */
            Bookmark bookmark = makeBookmark(
                    BOOKMARK_ID1,
                    feedInfo.getId()
            );

            /* When */
            Bookmark savedBookmark = bookmarkRepository.save(bookmark);

            /* Then */
            assertThat(savedBookmark).isEqualTo(bookmark);
        }
    }

    @DisplayName("Bookmark Delete 테스트")
    @Nested
    class DeleteTest {
        private Bookmark bookmark;

        @BeforeEach
        void setup() {
            FeedInfo feedInfo = feedInfoRepository.save(
                    makeSavedFeedInfo(FEED_INFO)
            );
            bookmark = bookmarkRepository.save(
                    makeBookmark(
                            BOOKMARK_ID1,
                            feedInfo.getId())
            );
        }

        @DisplayName("북마크 삭제에 성공한다")
        @Test
        void deleteSuccess() {
            /* Given */
            /* When */
            bookmarkRepository.delete(bookmark);

            /* Then */
            assertThat(bookmarkRepository.findById(bookmark.getId()))
                    .isEmpty();
        }
    }
}
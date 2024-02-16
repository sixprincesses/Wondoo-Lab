package com.wondoo.articleservice.bookmark.service;

import com.wondoo.articleservice.bookmark.data.cond.FindBookmarkCond;
import com.wondoo.articleservice.bookmark.data.request.BookmarkDeleteRequest;
import com.wondoo.articleservice.bookmark.data.request.BookmarkSaveRequest;
import com.wondoo.articleservice.bookmark.data.response.BookmarkDeleteResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarkSaveResponse;
import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.exception.BookmarkErrorCode;
import com.wondoo.articleservice.bookmark.exception.BookmarkException;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import com.wondoo.articleservice.bookmark.utils.BookmarkMapper;
import com.wondoo.articleservice.bookmark.utils.BookmarkServiceUtils;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.global.annotation.WondooMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.wondoo.articleservice.global.fixture.BookmarkFixture.*;
import static com.wondoo.articleservice.global.fixture.FeedInfoFixture.FEED_INFO;
import static com.wondoo.articleservice.global.fixture.FeedInfoFixture.makeSavedFeedInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WondooMockTest
class BookmarkModifyServiceTest {

    @InjectMocks
    private BookmarkServiceImpl bookmarkModifyService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private FeedInfoRepository feedInfoRepository;

    @Mock
    private BookmarkMapper bookmarkMapper;

    @DisplayName("Bookmark Save 테스트")
    @Nested
    class SaveTest {

        private final Long dummyMemberId = 1L;
        private final Long dummyFeedId = 1L;

        @DisplayName("저장에 성공한다")
        @Test
        void saveSuccess() {
            /* Given */
            BookmarkSaveRequest request = new BookmarkSaveRequest(1L);
            Bookmark bookmark = makeBookmark(BOOKMARK_ID1, dummyFeedId);
            Bookmark savedBookmark = makeSavedBookmark(BOOKMARK_ID1, dummyFeedId);
            FeedInfo findFeedInfo = makeSavedFeedInfo(FEED_INFO);

            given(feedInfoRepository.findById(1L))
                    .willReturn(Optional.of(findFeedInfo));
            given(bookmarkMapper.toEntity(1L, 1L))
                    .willReturn(bookmark);
            given(bookmarkRepository.save(bookmark))
                    .willReturn(savedBookmark);

            /* When */
            BookmarkSaveResponse response = bookmarkModifyService.save(dummyMemberId, request);

            /* Then */
            assertThat(response.bookmarkId()).isEqualTo(1L);
        }

        @DisplayName("중복 저장에 실패한다")
        @Test
        void duplicatedSaveFailed() {
            /* Given */
            BookmarkSaveRequest request = new BookmarkSaveRequest(1L);
            Bookmark savedBookmark = makeSavedBookmark(BOOKMARK_ID1, dummyFeedId);

            given(bookmarkRepository.findByBookmarkCond(any(FindBookmarkCond.class)))
                    .willReturn(Optional.of(savedBookmark));

            /* When */
            /* Then */
            assertThatThrownBy(() -> bookmarkModifyService.save(dummyMemberId, request))
                    .isInstanceOf(BookmarkException.class)
                    .hasMessageContaining(BookmarkErrorCode.ALREADY_BOOKMARKED_FEED.getMessage());
        }
    }

//    TODO: 추후 작성
//    @DisplayName("Bookmark Delete 테스트")
//    @Nested
//    class DeleteTest {
//        private Long bookmarkId = 1L;
//
//        @DisplayName("삭제에 성공한다")
//        @Test
//        void deleteSuccess() {
//            /* Given */
//            BookmarkDeleteRequest request = new BookmarkDeleteRequest(bookmarkId);
//            Bookmark bookmark = makeSavedBookmark(BOOKMARK_ID1, 1L);
//            given(BookmarkServiceUtils.findById(bookmarkRepository, request.bookmarkId()))
//                    .willReturn(bookmark);
//            /* When */
//            BookmarkDeleteResponse response = bookmarkModifyService.delete(1L, request);
//
//            /* Then */
//            assertThat(response.deletedBookmarkId()).isEqualTo(bookmarkId);
//        }
//
//        @DisplayName("삭제에 실패한다")
//        @Test
//        void deleteFailed() {
//            /* Given */
//            BookmarkDeleteRequest request = new BookmarkDeleteRequest(1L);
//            Bookmark savedBookmark = makeSavedBookmark(BOOKMARK_ID1_MEMBER_ID2, 1L);
//
//            given(BookmarkServiceUtils.findById(bookmarkRepository, request.bookmarkId()))
//                    .willReturn(savedBookmark);
//
//            /* When */
//
//            /* Then */
//            bookmarkModifyService.delete(1L, request);
//        }
//    }
}
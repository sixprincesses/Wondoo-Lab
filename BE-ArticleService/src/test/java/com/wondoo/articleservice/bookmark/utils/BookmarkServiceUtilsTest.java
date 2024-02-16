package com.wondoo.articleservice.bookmark.utils;

import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.exception.BookmarkErrorCode;
import com.wondoo.articleservice.bookmark.exception.BookmarkException;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import com.wondoo.articleservice.global.annotation.WondooMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

@WondooMockTest
class BookmarkServiceUtilsTest {
    @Mock
    private BookmarkRepository bookmarkRepository;

    @DisplayName("Find By Id 테스트")
    @Nested
    class FindByIdTest {
        private final Long dummyBookmarkId = 1L;

        @DisplayName("조회시 값이 있으면 예외를 던지지 않는다")
        @Test
        void findByIdSuccess() {
            /* Given */
            given(bookmarkRepository.findById(dummyBookmarkId)).willReturn(Optional.of(Bookmark.builder().build()));

            /* When */
            /* Then */
            assertThatCode(() -> BookmarkServiceUtils.findById(bookmarkRepository, 1L))
                    .doesNotThrowAnyException();
        }

        @DisplayName("조회시 값이 없으면 예외를 던진다")
        @Test
        void findByIdFailed() {
            /* Given */
            given(bookmarkRepository.findById(dummyBookmarkId)).willReturn(Optional.empty());

            /* When */
            /* Then */
            assertThatCode(() -> BookmarkServiceUtils.findById(bookmarkRepository, 1L))
                    .isInstanceOf(BookmarkException.class)
                    .hasMessageContaining(BookmarkErrorCode.NON_EXISTED_BOOKMARK.getMessage());
        }
    }
}
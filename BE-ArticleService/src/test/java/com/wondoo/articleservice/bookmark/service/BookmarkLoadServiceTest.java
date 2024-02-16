package com.wondoo.articleservice.bookmark.service;

import com.wondoo.articleservice.bookmark.data.cond.FindBookmarksCond;
import com.wondoo.articleservice.bookmark.data.request.BookmarksRequest;
import com.wondoo.articleservice.bookmark.data.response.BookmarksResponse;
import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import com.wondoo.articleservice.global.annotation.WondooMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.wondoo.articleservice.global.fixture.BookmarkFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WondooMockTest
class BookmarkLoadServiceTest {

    @InjectMocks
    private BookmarkServiceImpl bookmarkLoadService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @DisplayName("Find Bookmarks 테스트")
    @Nested
    class FindBookmarksTest {
        @DisplayName("조회에 성공한다")
        @Test
        void findBookmarksSuccess() {
            /* Given */
            List<Bookmark> bookmarks = new ArrayList<>(List.of(
                    makeSavedBookmark(BOOKMARK_ID1, 1L),
                    makeSavedBookmark(BOOKMARK_ID2, 1L))
            );
            given(bookmarkRepository.findBookmarks(any(FindBookmarksCond.class)))
                    .willReturn(bookmarks);

            /* When */
            BookmarksResponse response = bookmarkLoadService.findBookmarks(
                    1L, new BookmarksRequest(null, 2, null)
            );

            /* Then */
            assertThat(response.bookmarks()).hasSize(2);
        }
    }
}
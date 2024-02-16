package com.wondoo.articleservice.bookmark.service;

import com.wondoo.articleservice.bookmark.data.cond.FindBookmarkCond;
import com.wondoo.articleservice.bookmark.data.cond.FindBookmarksCond;
import com.wondoo.articleservice.bookmark.data.request.BookmarkDeleteRequest;
import com.wondoo.articleservice.bookmark.data.request.BookmarkSaveRequest;
import com.wondoo.articleservice.bookmark.data.request.BookmarksRequest;
import com.wondoo.articleservice.bookmark.data.response.BookmarkDeleteResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarkResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarkSaveResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarksResponse;
import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.exception.BookmarkErrorCode;
import com.wondoo.articleservice.bookmark.exception.BookmarkException;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import com.wondoo.articleservice.bookmark.utils.BookmarkMapper;
import com.wondoo.articleservice.bookmark.utils.BookmarkServiceUtils;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.feed.utils.FeedInfoServiceUtils;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.MemberInfoRepository;
import com.wondoo.articleservice.memberinfo.utils.MemberInfoServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkModifyService, BookmarkLoadService {
    private final BookmarkRepository bookmarkRepository;
    private final FeedInfoRepository feedInfoRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final BookmarkMapper mapper;

    @Transactional
    @Override
    public BookmarkSaveResponse save(Long memberId, BookmarkSaveRequest request) {
        validateDuplicateBookmark(memberId, request.feedId());
        FeedInfo feedInfo = FeedInfoServiceUtils.findById(feedInfoRepository, request.feedId());
        MemberInfo memberInfo = MemberInfoServiceUtils.findById(memberInfoRepository, memberId);
        bookmarkRepository.save(mapper.toEntity(memberInfo, feedInfo));
        return BookmarkSaveResponse.init();
    }

    private void validateDuplicateBookmark(Long memberId, Long feedId) {
        bookmarkRepository.findByBookmarkCond(FindBookmarkCond.of(memberId, feedId))
                .ifPresent(bookmark -> {
                    throw new BookmarkException(BookmarkErrorCode.ALREADY_BOOKMARKED_FEED);
                });
    }

    @Override
    public BookmarksResponse findBookmarks(Long memberId, BookmarksRequest request) {
        List<BookmarkResponse> bookmarks = bookmarkRepository.findBookmarks(
                        FindBookmarksCond.of(memberId, request))
                .stream()
                .map(BookmarkResponse::from)
                .toList();

        Long lastBookmarkId = getLastBookmarkId(bookmarks);

        return BookmarksResponse.of(
                bookmarks,
                lastBookmarkId
        );
    }

    private Long getLastBookmarkId(List<BookmarkResponse> bookmarks) {
        int lastIndex = bookmarks.size() - 1;
        if (lastIndex < 0) {
            return -1L;
        }
        BookmarkResponse bookmark = bookmarks.get(lastIndex);
        return bookmark.bookmarkId();
    }

    @Transactional
    @Override
    public BookmarkDeleteResponse delete(Long memberId, BookmarkDeleteRequest request) {
        Bookmark findBookmark = BookmarkServiceUtils.findByMemberIdAndFeedId(
                bookmarkRepository,
                memberId,
                request.feedId()
        );
        bookmarkRepository.delete(findBookmark);
        return BookmarkDeleteResponse.init();
    }
}

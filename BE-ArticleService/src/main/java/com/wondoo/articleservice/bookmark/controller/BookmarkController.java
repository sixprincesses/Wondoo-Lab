package com.wondoo.articleservice.bookmark.controller;

import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice.bookmark.data.request.BookmarkDeleteRequest;
import com.wondoo.articleservice.bookmark.data.request.BookmarkSaveRequest;
import com.wondoo.articleservice.bookmark.data.request.BookmarksRequest;
import com.wondoo.articleservice.bookmark.data.response.BookmarkDeleteResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarkSaveResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarksResponse;
import com.wondoo.articleservice.bookmark.service.BookmarkLoadService;
import com.wondoo.articleservice.bookmark.service.BookmarkModifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestWondooController
public class BookmarkController {

    private final BookmarkModifyService bookmarkModifyService;
    private final BookmarkLoadService bookmarkLoadService;

    @PostMapping("/auth/bookmark")
    public ResponseEntity<BookmarkSaveResponse> save(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid BookmarkSaveRequest request
    ) {
        BookmarkSaveResponse response = bookmarkModifyService.save(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/auth/bookmark")
    public ResponseEntity<BookmarksResponse> getBookmarks(
            @RequestHeader("member_id") Long memberId,
            @ModelAttribute BookmarksRequest request
    ) {
        BookmarksResponse response = bookmarkLoadService.findBookmarks(memberId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/auth/bookmark")
    public ResponseEntity<BookmarkDeleteResponse> delete(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid BookmarkDeleteRequest request
    ) {
        BookmarkDeleteResponse response = bookmarkModifyService.delete(memberId, request);
        return ResponseEntity.ok(response);
    }
}

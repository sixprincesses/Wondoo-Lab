package com.wondoo.articleservice.bookmark.utils;

import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {
    public Bookmark toEntity(MemberInfo memberInfo, FeedInfo feedInfo) {
        return Bookmark.builder()
                .memberInfo(memberInfo)
                .feedinfo(feedInfo)
                .build();
    }
}

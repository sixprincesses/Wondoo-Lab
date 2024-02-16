package com.wondoo.articleservice.feed.utils;

import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedInfoServiceUtils {
    public static FeedInfo findById(FeedInfoRepository repository, Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException()
        );
    }
}

package com.wondoo.articleservice.keyword.service;

import com.wondoo.articleservice.keyword.data.response.KeywordCountListResponse;
import com.wondoo.articleservice.keyword.data.response.KeywordRankListResponse;
import com.wondoo.articleservice.keyword.data.response.KeywordRankUpdateResponse;
import com.wondoo.articleservice.keyword.data.response.KeywordSearchResponse;
import com.wondoo.articleservice.keyword.domain.Keyword;
import com.wondoo.articleservice.keyword.domain.KeywordInfo;
import com.wondoo.articleservice.keyword.domain.KeywordRank;
import com.wondoo.articleservice.keyword.repository.KeywordInfoRepository;
import com.wondoo.articleservice.keyword.repository.KeywordRankRepository;
import com.wondoo.articleservice.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class KeywordService {

    private final KeywordInfoRepository keywordInfoRepository;
    private final KeywordRankRepository keywordRankRepository;
    private final KeywordRepository keywordRepository;

    public KeywordCountListResponse findKeywordCount(Long memberId) {
        return KeywordCountListResponse.builder()
                .keywordCountList(keywordInfoRepository.findKeywordCountByMemberId(memberId))
                .build();
    }

    public KeywordCountListResponse findKeywordCountWithZero(Long memberId) {
        return KeywordCountListResponse.builder()
                .keywordCountList(keywordInfoRepository.findKeywordCountWithZeroByMemberId(memberId))
                .build();
    }

    public KeywordRankListResponse findKeywordRank() {
        return KeywordRankListResponse.builder()
                .keywordRankList(keywordRankRepository.findKeywordRank())
                .build();
    }

    @Transactional
    public void createKeywordInfoList(Long memberId, Long feedId, List<String> keywordStringList) {
        for (String keywordString : keywordStringList) {
            Keyword keyword = keywordRepository.findByName(keywordString)
                    .orElseGet(() -> keywordRepository.save(Keyword.builder().name(keywordString).build()));
            keywordInfoRepository.save(
                    KeywordInfo.builder()
                            .keyword(keyword)
                            .memberId(memberId)
                            .feedId(feedId)
                            .build()
            );
            KeywordRank keywordRank = keywordRankRepository.findByKeyword_Id(keyword.getId())
                    .orElseGet(() -> keywordRankRepository.save(KeywordRank.builder()
                            .keyword(keyword)
                            .currCount(0L).prevCount(0L)
                            .build()));
            keyword.updateKeywordRank(keywordRank);
        }
    }

    @Transactional
    public void deleteKeywordInfo(Long feedId) {
        keywordInfoRepository.deleteAllByFeedId(feedId);
    }

    @Transactional
    public void updateKeywordRankList() {
        List<KeywordRankUpdateResponse> keywordRankUpdateList = keywordRepository.findAllKeywordRankCount();
        for (KeywordRankUpdateResponse keywordRankUpdateResponse : keywordRankUpdateList) {
            Keyword keyword = keywordRepository.findByName(keywordRankUpdateResponse.getName())
                    .orElseThrow(() -> new RuntimeException("키워드 조회 실패"));
            KeywordRank keywordRank = keywordRankRepository.findByKeyword_Id(keyword.getId())
                    .orElseGet(() -> keywordRankRepository.save(KeywordRank.builder()
                            .keyword(keyword)
                            .currCount(0L)
                            .prevCount(0L)
                            .build()));
            keywordRank.updateKeywordRank(
                    keywordRank.getCurrCount(),
                    keywordRankUpdateResponse.getCount()
            );
        }
    }

    public KeywordSearchResponse findKeywordSearchList(String keywordString) {
        return KeywordSearchResponse.builder()
                .keywordList(keywordRepository.findAllKeywordSearchList(keywordString))
                .build();
    }
}

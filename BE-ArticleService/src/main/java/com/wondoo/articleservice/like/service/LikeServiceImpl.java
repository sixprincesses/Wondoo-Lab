package com.wondoo.articleservice.like.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.articleservice._global.data.PointMessage;
import com.wondoo.articleservice._global.utils.KafkaProducer;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.feed.utils.FeedInfoServiceUtils;
import com.wondoo.articleservice.like.data.cond.FindLikeCond;
import com.wondoo.articleservice.like.data.cond.FindLikesCond;
import com.wondoo.articleservice.like.data.message.LikeMessage;
import com.wondoo.articleservice.like.data.request.LikeDeleteRequest;
import com.wondoo.articleservice.like.data.request.LikeSaveRequest;
import com.wondoo.articleservice.like.data.request.LikesRequest;
import com.wondoo.articleservice.like.data.response.LikeDeleteResponse;
import com.wondoo.articleservice.like.data.response.LikeResponse;
import com.wondoo.articleservice.like.data.response.LikeSaveResponse;
import com.wondoo.articleservice.like.data.response.LikesResponse;
import com.wondoo.articleservice.like.domain.Like;
import com.wondoo.articleservice.like.exception.LikeErrorCode;
import com.wondoo.articleservice.like.exception.LikeException;
import com.wondoo.articleservice.like.repository.LikeRepository;
import com.wondoo.articleservice.like.utils.LikeMapper;
import com.wondoo.articleservice.like.utils.LikeServiceUtils;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeLoadService, LikeModifyService {
    private final LikeRepository likeRepository;
    private final FeedInfoRepository feedInfoRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    private final LikeMapper mapper;

    @Transactional
    @Override
    public LikeSaveResponse save(Long memberId, LikeSaveRequest request) {
        validateDuplicateLike(memberId, request.feedId());
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원 정보가 존재하지 않습니다."));
        FeedInfo findFeedInfo = FeedInfoServiceUtils.findById(feedInfoRepository, request.feedId());

        if (!memberId.equals(findFeedInfo.getMemberInfo().getId())) {
            try {
                kafkaProducer.sendNotificationMessage(
                        "like",
                        objectMapper.writeValueAsString(
                                LikeMessage.builder()
                                        .targetId(findFeedInfo.getMemberInfo().getId())
                                        .url(findFeedInfo.getId())
                                        .content(memberInfo.getNickname())
                                        .build()
                        )
                );
                kafkaProducer.sendPointUpdateMessage(
                        "point",
                        objectMapper.writeValueAsString(
                                PointMessage.builder()
                                        .memberId(findFeedInfo.getMemberInfo().getId())
                                        .type("좋아요 보상")
                                        .point(5)
                                        .time(System.currentTimeMillis())
                                        .build()
                        )
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        likeRepository.save(mapper.toEntity(memberId, request.feedId()));
        return LikeSaveResponse.init();
    }

    private void validateDuplicateLike(Long memberId, Long feedId) {
        likeRepository.findByLikeCond(FindLikeCond.of(memberId, feedId)).ifPresent(like -> {
            throw new LikeException(LikeErrorCode.ALREADY_LIKE_FEED);
        });
    }

    @Override
    public LikesResponse findLikes(Long memberId, LikesRequest request) {
        List<LikeResponse> likes = likeRepository.findLikesCond(FindLikesCond.of(memberId, request))
                .stream()
                .map(LikeResponse::from)
                .toList();
        Long lastLikeId = getLastLikeId(likes);

        return LikesResponse.of(
                likes,
                lastLikeId
        );
    }

    private Long getLastLikeId(List<LikeResponse> likes) {
        int lastIndex = likes.size() - 1;
        if (lastIndex < 0) {
            return -1L;
        }
        return null;
    }

    @Transactional
    @Override
    public LikeDeleteResponse delete(Long memberId, LikeDeleteRequest request) {
        Like findLike = LikeServiceUtils.findByMemberIdAndFeedId(likeRepository, memberId, request.feedId());
        likeRepository.delete(findLike);
        try {
            kafkaProducer.sendPointUpdateMessage(
                    "point",
                    objectMapper.writeValueAsString(
                            PointMessage.builder()
                                    .memberId(memberId)
                                    .type("좋아요 보상 취소")
                                    .point(-5)
                                    .time(System.currentTimeMillis())
                                    .build()
                    )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return LikeDeleteResponse.init();
    }
}

package com.wondoo.articleservice.comment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.articleservice._global.utils.KafkaProducer;
import com.wondoo.articleservice.comment.data.message.CommentMessage;
import com.wondoo.articleservice.comment.data.request.CommentCreateRequest;
import com.wondoo.articleservice.comment.data.request.CommentUpdateRequest;
import com.wondoo.articleservice.comment.data.response.CommentChild;
import com.wondoo.articleservice.comment.data.response.CommentListResponse;
import com.wondoo.articleservice.comment.data.response.CommentParent;
import com.wondoo.articleservice.comment.domain.Comment;
import com.wondoo.articleservice.comment.repository.CommentRepository;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.feed.repository.FeedRepository;
import com.wondoo.articleservice.memberinfo.data.response.MemberInfoResponse;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final FeedInfoRepository feedInfoRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    public CommentListResponse findCommentList(Long feedId) {
        List<Comment> commentList = commentRepository.findAllByFeedIdOrderByCreatedTimeDesc(feedId);
        CommentListResponse response = new CommentListResponse();
        Map<Long, List<CommentChild>> replyMap = new HashMap<>();
        for (Comment comment : commentList) {
            MemberInfo memberInfo = memberInfoRepository.findById(comment.getMemberId())
                    .orElseThrow(() -> new RuntimeException("댓글 회원 정보 조회 실패"));
            MemberInfoResponse memberInfoResponse = MemberInfoResponse.builder()
                    .memberId(comment.getMemberId())
                    .nickname(memberInfo.getNickname())
                    .imageurl(memberInfo.getImageurl())
                    .level(memberInfo.getLevel())
                    .build();

            if (!replyMap.containsKey(comment.getParentId())) {
                replyMap.put(comment.getParentId(), new ArrayList<>());
            }
            if (comment.getParentId() == null) {
                CommentParent commentResponse = CommentParent.builder()
                        .commentId(comment.getId())
                        .member(memberInfoResponse)
                        .isDeleted(comment.isDeleted())
                        .content(comment.isDeleted() ? "삭제된 메세지입니다" : comment.getContent())
                        .parentId(comment.getParentId())
                        .feedId(comment.getFeedId())
                        .createdTime(comment.getCreatedTime())
                        .updatedTime(comment.getUpdatedTime())
                        .build();
                response.getCommentList().add(commentResponse);
            } else {
                CommentChild commentChild = CommentChild.builder()
                        .commentId(comment.getId())
                        .member(memberInfoResponse)
                        .isDeleted(comment.isDeleted())
                        .content(comment.isDeleted() ? "삭제된 메세지입니다" : comment.getContent())
                        .feedId(comment.getFeedId())
                        .createdTime(comment.getCreatedTime())
                        .updatedTime(comment.getUpdatedTime())
                        .build();
                replyMap.get(comment.getParentId()).add(commentChild);
            }
        }
        for (CommentParent commentParent : response.getCommentList()) {
            commentParent.setReplylist(replyMap.get(commentParent.getCommentId()));
        }
        return response;
    }

    @Transactional
    public void createComment(Long memberId, CommentCreateRequest request) throws JsonProcessingException {
        Comment comment = commentRepository.save(Comment.builder()
                .feedId(request.getFeedId())
                .memberId(memberId)
                .parentId(request.getParentId())
                .content(request.getContent())
                .isDeleted(false)
                .build());
        FeedInfo feedInfo = feedInfoRepository.findById(request.getFeedId())
                .orElseThrow(() -> new RuntimeException("피드 정보 조회 실패"));
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("피드 회원 정보 조회 실패"));

        if (request.getParentId() != null) {
            Comment parentComment = commentRepository.findById(request.getParentId()).get();
            if (!memberId.equals(parentComment.getMemberId())) {
                kafkaProducing(
                        "comment_comment",
                        parentComment.getMemberId(),
                        feedInfo.getId(),
                        parentComment.getId(),
                        memberInfo.getNickname()
                );
                return;
            }
        }
        if (!memberId.equals(feedInfo.getMemberInfo().getId())) {
            kafkaProducing(
                    "comment",
                    feedInfo.getMemberInfo().getId(),
                    feedInfo.getId(),
                    comment.getId(),
                    memberInfo.getNickname()
            );
        }

    }

    @Transactional
    public void updateComment(Long memberId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new RuntimeException("댓글 조회 실패"));
        if (!memberId.equals(comment.getMemberId())) {
            throw new RuntimeException("댓글 회원 불일치");
        }
        comment.updateComment(request.getContent());
    }

    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 조회 실패"));
        if (!memberId.equals(comment.getMemberId())) {
            throw new RuntimeException("댓글 회원 불일치");
        }
        comment.deleteComment();
    }

    private void kafkaProducing(String key, Long targetId, Long url, Long subUrl, String content) throws JsonProcessingException {
        kafkaProducer.sendNotificationMessage(
                key,
                objectMapper.writeValueAsString(
                        CommentMessage.builder()
                                .targetId(targetId)
                                .url(url)
                                .subUrl(subUrl)
                                .content(content)
                                .build()
                )
        );
    }
}

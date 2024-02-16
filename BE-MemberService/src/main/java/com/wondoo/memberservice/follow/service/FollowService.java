package com.wondoo.memberservice.follow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.follow.data.message.FollowMessage;
import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.follow.exception.FollowErrorCode;
import com.wondoo.memberservice.follow.exception.FollowException;
import com.wondoo.memberservice.follow.repository.FollowRepository;
import com.wondoo.memberservice.global.exception.ServerErrorCode;
import com.wondoo.memberservice.global.exception.ServerException;
import com.wondoo.memberservice.global.utils.KafkaProducer;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FollowService implements FollowSaveService, FollowLoadService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    /**
     * 팔로우 로직 구현
     * 자기 자신을 팔로우 못하도록 제한
     *
     * @param toId     팔로우 받는 사람
     * @param memberId 팔로우 요청하는 사람
     */
    @Transactional
    @Override
    public void memberFollow(Long toId, Long memberId) {

        if (toId == memberId) {
            throw new FollowException(FollowErrorCode.FOLLOW_BAD_REQUEST);
        }

        // 이미 팔로우한 관계인지 체크
        Optional<Follow> followCheck = followRepository.findByFollow(toId, memberId);

        if (followCheck.isEmpty()) {
            Optional<Follow> friendCheck = followRepository.findByFollow(memberId, toId);
            // 맞팔로우 관계 체크
            friendCheck.ifPresent(
                    follow -> follow.friendUpdate(true)
            );

            Member to = findById(toId);
            Member from = findById(memberId);

            followRepository.save(Follow.builder()
                    .to(to)
                    .from(from)
                    .isFriend(friendCheck.isPresent())
                    .build());
            to.getStatistic()
                    .followerCalculate(true);
            from.getStatistic()
                    .followingCalculate(true);

            try {
                kafkaProducer.sendNotificationMessage(
                        "follow",
                        objectMapper.writeValueAsString(
                                FollowMessage.builder()
                                        .targetId(to.getId())
                                        .url(from.getId())
                                        .content(from.getNickname())
                                        .build()
                        )
                );
            } catch (JsonProcessingException e) {
                throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
            }

            return;
        }
        throw new FollowException(FollowErrorCode.FOLLOW_DUPLICATE_REQUEST);

    }

    /**
     * 언팔로우 로직 구현
     * 자기 자신을 언팔로우 못하도록 제한
     * 이미 팔로우 한 상태인지 확인
     *
     * @param toId     팔로우 받는 사람
     * @param memberId 팔로우 요청하는 사람
     */
    @Transactional
    @Override
    public void memberUnfollow(Long toId, Long memberId) {

        if (toId == memberId) {
            throw new FollowException(FollowErrorCode.FOLLOW_BAD_REQUEST);
        }

        followRepository.delete(
                followRepository.findByFollow(toId, memberId)
                        .orElseThrow(
                                () -> new FollowException(FollowErrorCode.FOLLOW_NOT_FOUND)
                        )
        );

        Optional<Follow> friendChk = followRepository.findByFollow(memberId, toId);
        friendChk.ifPresent(
                follow -> follow.friendUpdate(false)
        );

        Member to = findById(toId);
        Member from = findById(memberId);

        to.getStatistic()
                .followerCalculate(false);
        from.getStatistic()
                .followingCalculate(false);
    }

    /**
     * 페이지네이션 정보 기반 팔로워 조회
     * 현재 팔로워 닉네임만 조회되는데 추후 이미지도 조회 가능하도록 수정 예정
     *
     * @param memberId 조회할 기준 member_id
     * @param pageable 페이지네이션 정보 (page_size, page_offset)
     * @return Page 정보
     */
    @Override
    public Page<FollowsResponse> followersLoad(Long memberId, Pageable pageable) {

        findById(memberId);
        return followRepository.followersLoad(memberId, pageable);
    }

    /**
     * 페이지네이션 정보 기반 팔로잉 조회
     * 현재 팔로잉 닉네임만 조회되는데 추후 이미지도 조회 가능하도록 수정 예정
     *
     * @param memberId 조회할 기준 member_id
     * @param pageable 페이지네이션 정보 (page_size, page_offset)
     * @return Page 정보
     */
    @Override
    public Page<FollowsResponse> followingsLoad(Long memberId, Pageable pageable) {

        findById(memberId);
        return followRepository.followingsLoad(memberId, pageable);
    }

    /**
     * 페이지네이션 정보 기반 친구 조회
     * 현재 친구 닉네임만 조회되는데 추후 이미지도 조회 가능하도록 수정 예정
     *
     * @param memberId 조회할 기준 member_id
     * @param pageable 페이지네이션 정보 (page_size, page_offset)
     * @return Page 정보
     */
    @Override
    public Page<FollowsResponse> friendsLoad(Long memberId, Pageable pageable) {

        findById(memberId);
        return followRepository.friendsLoad(memberId, pageable);
    }

    private Member findBySocialId(Long socialId) {
        return memberRepository.findBySocialId(socialId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }
}

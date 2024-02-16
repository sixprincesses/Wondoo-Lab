package com.wondoo.memberservice.point.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.global.exception.ServerErrorCode;
import com.wondoo.memberservice.global.exception.ServerException;
import com.wondoo.memberservice.member.data.request.MemberRankingInitRequest;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.repository.MemberRepository;
import com.wondoo.memberservice.point.data.message.PointMessage;
import com.wondoo.memberservice.point.domain.PointReceipt;
import com.wondoo.memberservice.point.repository.PointReceiptRepository;
import com.wondoo.memberservice.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PointService implements PointSaveService {

    private final MemberRepository memberRepository;
    private final PointReceiptRepository pointReceiptRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    @Override
    public void pointSave(PointMessage pointMessage) {

        Member member = memberRepository.findById(pointMessage.memberId()).get();
        // 포인트 부여
        member.getPoint()
                .pointUpdate(pointMessage.point());
        log.info("------POINT-------: [{}]", pointMessage.point());
        // 영수증 발급
        PointReceipt pointReceipt = pointReceiptRepository.save(
                PointReceipt.builder()
                        .memberId(member.getId())
                        .type(pointMessage.type())
                        .point(pointMessage.point())
                        .time(pointMessage.time())
                        .build()
        );
        log.info("-------POINT RECEIPT--------: [{}]", pointReceipt);

        try {
            log.info("-----RANKING START------");
            String memberInfo = objectMapper.writeValueAsString(MemberRankingInitRequest.builder()
                    .memberId(member.getId())
                    .build());
            log.info("--------TARGET------");
            if (Boolean.TRUE.equals(redisTemplate.hasKey("member_ranking"))) {
                redisTemplate.opsForZSet().incrementScore("member_ranking", memberInfo, pointMessage.point());
                log.info("--------DONE-------");
            }

        } catch (JsonProcessingException e){
            throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
        }
    }
}

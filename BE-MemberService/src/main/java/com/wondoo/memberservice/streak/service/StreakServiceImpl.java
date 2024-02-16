package com.wondoo.memberservice.streak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.repository.MemberRepository;
import com.wondoo.memberservice.member.utils.MemberServiceUtils;
import com.wondoo.memberservice.streak.data.message.KafkaStreakKey;
import com.wondoo.memberservice.streak.data.message.StreakMessage;
import com.wondoo.memberservice.streak.data.response.GrassResponse;
import com.wondoo.memberservice.streak.data.response.StreakResponse;
import com.wondoo.memberservice.streak.domain.Streak;
import com.wondoo.memberservice.streak.repository.StreakRepository;
import com.wondoo.memberservice.streak.utils.StreakServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StreakServiceImpl implements StreakLoadService, StreakModifyService {
    private final StreakRepository streakRepository;
    private final MemberRepository memberRepository;
    private final ObjectMapper mapper;

    @Override
    public StreakResponse findMemberStreak(Long memberId) {
        List<GrassResponse> grass = streakRepository.findStreaks(memberId, LocalDate.now())
                .stream()
                .map(GrassResponse::from)
                .toList();

        int currentStreak = getCurrentStreak(grass);
        return StreakResponse.of(grass, currentStreak);
    }

    private int getCurrentStreak(final List<GrassResponse> grass) {
        int currentStreak = 0;
        LocalDate now = LocalDate.now();
        for (int i = grass.size() - 1; i >= 0; i--) {
            if (!now.minusDays(currentStreak).equals(grass.get(i).date())) {
                break;
            }
            currentStreak++;
        }
        return currentStreak;
    }

    @KafkaListener(
            topics = "${kafka.topic.article.updateinfo}",
            groupId = "${kafka.group.member}",
            containerFactory = "kafkaListener"
    )
    @Transactional
    @Override
    public Streak update(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Payload String kafkaMessage) {
        StreakMessage streakMessage = parseStreakMessage(kafkaMessage);
        Member member = MemberServiceUtils.findById(
                memberRepository,
                streakMessage.memberId()
        );
        if (key.equalsIgnoreCase(KafkaStreakKey.SAVE.getValue())) {
            return saveFeedMessage(streakMessage, member);
        }
        if (key.equalsIgnoreCase(KafkaStreakKey.DELETE.getValue())) {
            return deleteFeedMessage(streakMessage);
        }
        throw new RuntimeException("잘못된 KafkaMessage 요청입니다");
    }

    private Streak saveFeedMessage(StreakMessage streakMessage, Member member) {
        Streak streak = StreakServiceUtils.findByKafkaMessageOrDefault(
                streakRepository,
                streakMessage,
                member
        );
        streak.changeCount(1);
        log.info("saveFeedMessage : [{}], Streak : [{}]",
                streakMessage, streak);
        return streakRepository.save(streak);
    }

    private Streak deleteFeedMessage(StreakMessage streakMessage) {
        Streak streak = StreakServiceUtils.findByKafkaMessage(
                streakRepository,
                streakMessage
        );
        streak.changeCount(-1);
        if (streak.canDelete()) {
            streakRepository.delete(streak);
        }
        return streak;
    }

    private StreakMessage parseStreakMessage(String kafkaMessage) {
        try {
            return mapper.readValue(kafkaMessage, StreakMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("KafkaMessage 파싱에 실패하였습니다");
        }
    }
}

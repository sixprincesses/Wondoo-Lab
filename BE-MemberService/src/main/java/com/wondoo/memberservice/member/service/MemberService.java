package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements MemberSaveService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입 / 로그인
     *
     * @param githubUserInfoResponse Github 에 등록된 사용자 정보
     * @return 추후 JWT 발급으로 변경
     */
    @Transactional
    @Override
    public ResponseEntity<String> memberSave(GithubUserInfoResponse githubUserInfoResponse) {

        Optional<Member> tmpMember = memberRepository.findBySocialId(githubUserInfoResponse.socialId());

        if (tmpMember.isEmpty()) {
            Member member = memberRepository.save(Member.builder()
                    .socialId(githubUserInfoResponse.socialId())
                    .socialNickname(githubUserInfoResponse.socialNickname())
                    .build());

            return ResponseEntity.status(HttpStatus.CREATED).body("sign up");
        }

        // Github Nickname 을 변경했으면 반영
        if (!githubUserInfoResponse.socialNickname().equals(tmpMember.get().getSocialNickname())) {
            tmpMember.get().updateSocialNickname(githubUserInfoResponse.socialNickname());
        }

        return ResponseEntity.status(HttpStatus.OK).body("login");
    }
}

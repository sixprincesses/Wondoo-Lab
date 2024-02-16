package com.wondoo.memberservice.diary.service;

import com.wondoo.memberservice.diary.data.response.DiarySaveResponse;
import com.wondoo.memberservice.diary.domain.CommitDetail;
import com.wondoo.memberservice.diary.domain.Diary;
import com.wondoo.memberservice.diary.exception.DiaryErrorCode;
import com.wondoo.memberservice.diary.exception.DiaryException;
import com.wondoo.memberservice.diary.repository.DiaryRepository;
import com.wondoo.memberservice.global.exception.ServerErrorCode;
import com.wondoo.memberservice.global.exception.ServerException;
import com.wondoo.memberservice.infra.OpenAiClient;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DiaryService implements DiarySaveService, DiaryLoadService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final OpenAiClient openAiClient;

    @Value("${github.token}")
    private String token;
    @Value("${openai-token}")
    private String openaiToken;

    /**
     * 당일 전체 commit 기반 Diary 생성
     *
     * @param memberId 요청자 member_id
     * @throws IOException 에러 처리
     */
    @Transactional
    @Override
    public DiarySaveResponse diarySave(Long memberId, LocalDate localDate) throws IOException {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        GitHub github;
        try {
            github = new GitHubBuilder().withOAuthToken(token).build();
            github.checkApiUrlValidity();
        } catch (IOException e) {
            throw new IllegalArgumentException("failed to connect gitHub");
        }
        log.info("--------Github------- : [{}]", github);
        PagedSearchIterable<GHCommit> commits = getCommits(github, member, localDate);
        if (commits.getTotalCount() == 0) {
            return null;
        }
        log.info("-----Commits----- : [{}]", commits);
        Map<String, List<CommitDetail>> commitsByRepository = new HashMap<>();
        StringBuilder content = commitToPrompt(commits, commitsByRepository);
        log.info("-------Prompt----- [{}]", content);
        String diaryContent = openAiClient.diaryByPrompt(memberId, openaiToken, content.toString());
        log.info("-------Result----- [{}]", diaryContent);
        if (diaryContent != null) {
            Optional<Diary> byMemberIdAndDate = diaryRepository.findByMemberIdAndDate(memberId, localDate);
            if (byMemberIdAndDate.isPresent()) {
                Diary diary = byMemberIdAndDate.get();
                diary.DiaryUpdate(diaryContent, commitsByRepository.keySet(), commits.getTotalCount(), commitsByRepository);
                diaryRepository.save(diary);
                return DiarySaveResponse.builder()
                        .id(diary.getId())
                        .commitCount(diary.getCommitCount())
                        .content(diary.getContent())
                        .date(diary.getDate())
                        .memberId(diary.getMemberId())
                        .repository(diary.getRepository())
                        .log(diary.getLog())
                        .build();
            }
            Diary diary = diaryRepository.save(
                    Diary.builder()
                            .memberId(memberId)
                            .commitCount(commits.getTotalCount())
                            .date(localDate)
                            .log(commitsByRepository)
                            .content(diaryContent)
                            .repository(commitsByRepository.keySet())
                            .build()
            );
            return DiarySaveResponse.builder()
                    .id(diary.getId())
                    .commitCount(diary.getCommitCount())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .memberId(diary.getMemberId())
                    .repository(diary.getRepository())
                    .log(diary.getLog())
                    .build();
        }

        return null;
    }

    /**
     * Diary 자동 생성
     */
    @Transactional
    @Override
    public void diarySaveAuto() {
        List<Member> members = memberRepository.findAll();
        if (!members.isEmpty()) {
            members.forEach(
                    member -> {
                        try {
                            Optional<Diary> byMemberIdAndDate = diaryRepository.findByMemberIdAndDate(member.getId(), LocalDate.now().minusDays(1));
                            if (byMemberIdAndDate.isEmpty()) {
                                diarySave(member.getId(), LocalDate.now().minusDays(1));
                            }
                        } catch (IOException e) {
                            throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
                        }
                    }
            );
        }
    }

    @Transactional
    @Override
    public void diaryDelete(Long memberId, String diaryId) {
        memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(
                        () -> new DiaryException(DiaryErrorCode.DIARY_NOT_FOUND)
                );
        if (memberId != diary.getMemberId()) {
            throw new DiaryException(DiaryErrorCode.INVALID_MEMBER_REQUEST);
        }
        diaryRepository.delete(diary);
    }

    private static StringBuilder commitToPrompt(PagedSearchIterable<GHCommit> commits, Map<String, List<CommitDetail>> commitsByRepository) throws IOException {
        StringBuilder content = new StringBuilder();
        log.info("===========commits========== [{}]", commits);
        for (GHCommit commit : commits) {

            String repository = commit.getOwner().getName();
            log.info("------Repository-------: [{}]", repository);
            List<CommitDetail> repoCommits = commitsByRepository.getOrDefault(repository, new ArrayList<>());
            String commitMessage = commit.getCommitShortInfo().getMessage();
            repoCommits.add(
                    CommitDetail.builder()
                            .commitId(commit.getSHA1())
                            .content(commitMessage)
                            .time(commit.getCommitDate())
                            .build()
            );
            commitsByRepository.put(repository, repoCommits);

            String commitContent = commit.getCommitDate() + ": " + commitMessage;
            content.append(commitContent).append("\n");
            System.out.println("----------------------------------------");
        }

        String prompt = "위 내용으로 오늘 어떤 것들을 했는 지 정리해서 다이어리 형태로 작성해줘";
        content.append(prompt);
        return content;
    }

    private static PagedSearchIterable<GHCommit> getCommits(GitHub github, Member member, LocalDate currentDate) {
        String dateFilter = "author-date:" + currentDate.toString();

        GHCommitSearchBuilder builder = github.searchCommits()
                .author(member.getSocialNickname())
                .sort(GHCommitSearchBuilder.Sort.AUTHOR_DATE)
                .q(dateFilter);
        PagedSearchIterable<GHCommit> commits = builder.list();
        return commits;
    }

    @Override
    public Page<DiarySaveResponse> diaryLoadAll(Long memberId, Pageable pageable) {
        return diaryRepository.findByMemberId(memberId, pageable);
    }
}

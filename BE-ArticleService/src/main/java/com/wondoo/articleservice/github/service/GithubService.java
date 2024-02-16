package com.wondoo.articleservice.github.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wondoo.articleservice._global.infrastructure.GithubClient;
import com.wondoo.articleservice.github.data.response.BranchListResponse;
import com.wondoo.articleservice.github.data.response.ChangedFileListResponse;
import com.wondoo.articleservice.github.data.response.CommitListResponse;
import com.wondoo.articleservice.github.data.response.RepositoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient gitClient;

    public RepositoryListResponse getRepositoryList(String nickname) throws JsonProcessingException {
        return gitClient.getRepositoryList(nickname);
    }

    public BranchListResponse getBranchList(String nickname, String repository) throws JsonProcessingException {
        return gitClient.getBranchList(nickname, repository);
    }

    public CommitListResponse getCommitList(String nickname, String repository, String branch) throws JsonProcessingException {
        return gitClient.getCommitList(nickname, repository, branch);
    }

    public ChangedFileListResponse getChangedFileList(String nickname, String repository, String commit) throws JsonProcessingException {
        return gitClient.getChangedFileList(nickname, repository, commit);
    }
}

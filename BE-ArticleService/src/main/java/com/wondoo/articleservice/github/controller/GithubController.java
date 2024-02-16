package com.wondoo.articleservice.github.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice._global.data.ApiResponse;
import com.wondoo.articleservice._global.data.StatusCode;
import com.wondoo.articleservice.github.data.response.BranchListResponse;
import com.wondoo.articleservice.github.data.response.ChangedFileListResponse;
import com.wondoo.articleservice.github.data.response.CommitListResponse;
import com.wondoo.articleservice.github.data.response.RepositoryListResponse;
import com.wondoo.articleservice.github.service.GithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/github/repository")
    public ResponseEntity<RepositoryListResponse> getRepositoryList(
            @RequestParam("nickname") String nickname
    ) throws JsonProcessingException {
        RepositoryListResponse repositoryList = githubService.getRepositoryList(nickname);
        // ApiResponse<RepositoryListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_EXTERNAL_API, repositoryList);
        return ResponseEntity.status(HttpStatus.OK).body(repositoryList);
    }
    @GetMapping("/github/branch")
    public ResponseEntity<BranchListResponse> getBranchList(
            @RequestParam("nickname") String nickname,
            @RequestParam("repository") String repository
    ) throws JsonProcessingException {
        BranchListResponse branchList = githubService.getBranchList(nickname, repository);
        //ApiResponse<BranchListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_EXTERNAL_API, branchList);
        return ResponseEntity.status(HttpStatus.OK).body(branchList);
    }
    @GetMapping("/github/commit")
    public ResponseEntity<CommitListResponse> getCommitList(
            @RequestParam("nickname") String nickname,
            @RequestParam("repository") String repository,
            @RequestParam("branch") String branch
    ) throws JsonProcessingException {
        CommitListResponse commitList = githubService.getCommitList(nickname, repository, branch);
        // ApiResponse<CommitListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_EXTERNAL_API, commitList);
        return ResponseEntity.status(HttpStatus.OK).body(commitList);
    }

    @GetMapping("/github/changedfile")
    public ResponseEntity<ChangedFileListResponse> getChangedFileList(
            @RequestParam("nickname") String nickname,
            @RequestParam("repository") String repository,
            @RequestParam("commit") String commit
    ) throws JsonProcessingException {
        ChangedFileListResponse changedFileList = githubService.getChangedFileList(nickname, repository, commit);
        // ApiResponse<ChangedFileListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_EXTERNAL_API, changedFileList);
        return ResponseEntity.status(HttpStatus.OK).body(changedFileList);
    }
}

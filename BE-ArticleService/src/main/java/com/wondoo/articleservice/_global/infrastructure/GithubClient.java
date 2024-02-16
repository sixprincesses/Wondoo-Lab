package com.wondoo.articleservice._global.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.articleservice.github.data.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GithubClient {
    private final RestTemplate restTemplate;
    @Value("${github.apiurl}")
    private String githubApiUrl;
    @Value("${github.token}")
    private String githubToken;

    public RepositoryListResponse getRepositoryList(String nickname) throws JsonProcessingException {
        String uri = "/users/" + nickname + "/repos";
        ObjectMapper mapper = new ObjectMapper();
        List<RepositoryResponse> repositoryList = Arrays.asList(mapper.readValue(githubAPI(uri), RepositoryResponse[].class));
        RepositoryListResponse response = new RepositoryListResponse();
        for (RepositoryResponse repository : repositoryList) {
            response.getRepositoryList().add(repository.getRepositoryName());
        }

        return response;
    }

    public BranchListResponse getBranchList(String nickname, String repository) throws JsonProcessingException {
        String uri = "/repos/" + nickname + "/" + repository + "/branches";
        ObjectMapper mapper = new ObjectMapper();
        List<BranchResponse> branchList = Arrays.asList(mapper.readValue(githubAPI(uri), BranchResponse[].class));
        BranchListResponse response = new BranchListResponse();
        for (BranchResponse branch : branchList) {
            response.getBranchList().add(branch.getBranchName());
        }
        return response;
    }

    public CommitListResponse getCommitList(String nickname, String repository, String branch) throws JsonProcessingException {
        String uri = "/repos/" + nickname + "/" + repository + "/commits?sha=" + branch;
        ObjectMapper mapper = new ObjectMapper();
        List<CommitResponse> commitList = Arrays.asList(mapper.readValue(githubAPI(uri), CommitResponse[].class));
        CommitListResponse response = new CommitListResponse();
        for (CommitResponse commitResponse : commitList) {
            Commit commit = Commit.builder()
                    .sha(commitResponse.getSha())
                    .message(commitResponse.getMessage())
                    .build();
            response.getCommitList().add(commit);
        }
        return response;
    }

    public ChangedFileListResponse getChangedFileList(String nickname, String repository, String commit) throws JsonProcessingException {
        String uri = "/repos/" + nickname + "/" + repository + "/commits/" + commit;

        ObjectMapper mapper = new ObjectMapper();
        ChangedFileResponse changedFileList = mapper.readValue(githubAPI(uri), ChangedFileResponse.class);

        ChangedFileListResponse response = new ChangedFileListResponse();
        for (ChangedFile changedFile: changedFileList.getFiles()) {
            response.getChangedFileList().add(changedFile);
        }
        return response;
    }

    private String githubAPI(String uri) {
        String url = githubApiUrl + uri;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBasicAuth("Bearer " + githubToken);
        HttpEntity request = new HttpEntity(header);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        ).getBody();
    }
}

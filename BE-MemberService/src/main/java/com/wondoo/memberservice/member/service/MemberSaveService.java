package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.client.github.data.response.GithubUserInfoResponse;
import org.springframework.http.ResponseEntity;

public interface MemberSaveService {

    ResponseEntity<String> memberSave(GithubUserInfoResponse githubUserInfoResponse);
}

package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.request.TokenUpdateRequest;
import com.wondoo.memberservice.member.data.response.ImageResponse;
import com.wondoo.memberservice.member.data.response.ImageSaveResponse;
import com.wondoo.memberservice.member.data.response.MemberUpdateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberSaveService {

    MemberUpdateResponse memberUpdate(Long memberId, MemberUpdateRequest memberUpdateRequest);

    void memberRankingInit();

    void memberLevelReload();

    void updateOpenaiToken(Long memberId, TokenUpdateRequest request);

    void updateGithubToken(Long memberId, TokenUpdateRequest request);

    ImageResponse updateImage(Long memberId, MultipartFile image) throws IOException;

    ImageResponse updateCoverImage(Long memberId, MultipartFile image) throws IOException;
}

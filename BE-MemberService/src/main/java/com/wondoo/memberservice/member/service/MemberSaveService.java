package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberSaveService {

    void memberUpdate(Long memberId, Long socialId, MemberUpdateRequest memberUpdateRequest, MultipartFile imageId) throws IOException;
}

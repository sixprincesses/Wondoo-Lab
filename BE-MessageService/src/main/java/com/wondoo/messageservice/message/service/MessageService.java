package com.wondoo.messageservice.message.service;

import com.wondoo.messageservice.memberinfo.data.MemberInfo;
import com.wondoo.messageservice.memberinfo.repository.MemberInfoRepository;
import com.wondoo.messageservice.message.data.Message;
import com.wondoo.messageservice.message.domain.request.MessageRequest;
import com.wondoo.messageservice.message.domain.response.MessageListResponse;
import com.wondoo.messageservice.message.domain.response.MessageResponse;
import com.wondoo.messageservice.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberInfoRepository memberInfoRepository;

    public MessageListResponse saveMessage(MessageRequest request) {
        Message message = messageRepository.save(Message.builder()
                .memberId(request.getMemberId())
                .content(request.getContent())
                .reference(request.getReference())
                .messageType(request.getMessageType())
                .build());
        MessageListResponse response = new MessageListResponse();
        response.getMessageList().add(MessageResponse.builder()
                .memberId(message.getMemberId())
                .reference(message.getReference())
                .content(message.getContent())
                .nickname(memberInfoRepository.findNicknameById(message.getMemberId()))
                .imageurl(memberInfoRepository.findImageurlById(message.getMemberId()))
                .messageType(message.getMessageType())
                .createdTime(message.getCreatedTime())
                .build());
        return response;
    }
}

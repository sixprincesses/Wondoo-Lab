package com.wondoo.messageservice.channel.service;

import com.wondoo.messageservice._global.type.AccessType;
import com.wondoo.messageservice._global.type.ChannelType;
import com.wondoo.messageservice._global.utils.UUIDFactory;
import com.wondoo.messageservice.channel.data.Channel;
import com.wondoo.messageservice.channel.data.ChannelMember;
import com.wondoo.messageservice.channel.domain.request.MultipleChannelRequest;
import com.wondoo.messageservice.channel.domain.request.SimpleChannelRequest;
import com.wondoo.messageservice.channel.domain.response.ChannelListResponse;
import com.wondoo.messageservice.channel.domain.response.ChannelResponse;
import com.wondoo.messageservice.channel.repository.ChannelMemberRepository;
import com.wondoo.messageservice.channel.repository.ChannelRepository;
import com.wondoo.messageservice.memberinfo.repository.MemberInfoRepository;
import com.wondoo.messageservice.message.data.Message;
import com.wondoo.messageservice.message.domain.response.MessageListResponse;
import com.wondoo.messageservice.message.domain.response.MessageResponse;
import com.wondoo.messageservice.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final ChannelMemberRepository channelMemberRepository;

    private final UUIDFactory uuidFactory;

    public String findOppositeNickname(Long memberId, String reference) {
        Long oppositeMemberId = channelMemberRepository.findMemberIdByMemberIdAndReference(memberId, reference);
        return memberInfoRepository.findNicknameById(oppositeMemberId);
    }

    public String findOppositeImageurl(Long memberId, String reference) {
        Long oppositeMemberId = channelMemberRepository.findMemberIdByMemberIdAndReference(memberId, reference);
        return memberInfoRepository.findImageurlById(oppositeMemberId);
    }

    public MessageResponse findLastMessage(String reference) {
        Message message = messageRepository.findLastMessageByReference(reference);
        if (message == null) {
            return null;
        }
        return MessageResponse.builder()
                .memberId(message.getMemberId())
                .content(message.getContent())
                .reference(message.getReference())
                .nickname(memberInfoRepository.findNicknameById(message.getMemberId()))
                .createdTime(message.getCreatedTime())
                .messageType(message.getMessageType())
                .build();
    }

    public ChannelListResponse findChannelListByMemberId(Long memberId) {
        List<String> channelReferenceList = channelMemberRepository.findByMemberId(memberId);
        List<Channel> channelList = channelRepository.findByReferenceIn(channelReferenceList);
        ChannelListResponse response = new ChannelListResponse();
        for (Channel channel : channelList) {
            response.getChannelList().add(
                    ChannelResponse.builder()
                            .name(channel.getChannelType() == ChannelType.SIMPLE
                                    ? findOppositeNickname(memberId, channel.getReference()) : channel.getName())
                            .reference(channel.getReference())
                            .imageurl(channel.getChannelType() == ChannelType.SIMPLE
                                    ? findOppositeImageurl(memberId, channel.getReference())
                                    : "https://m.harvestbean.co.kr/web/product/extra/big/202008/5822bf9ee076bb1865b0a1c405be6601.jpg")
                            .lastMessage(findLastMessage(channel.getReference()))
                            .build()
            );
        }
        return response;
    }

    public MessageListResponse findMessageListByChannelId(String reference) {
        List<Message> messageList = messageRepository.findByReference(reference);
        MessageListResponse response = new MessageListResponse();
        for (Message message : messageList) {
            response.getMessageList().add(
                    MessageResponse.builder()
                            .content(message.getContent())
                            .memberId(message.getMemberId())
                            .nickname(memberInfoRepository.findNicknameById(message.getMemberId()))
                            .imageurl(memberInfoRepository.findImageurlById(message.getMemberId()))
                            .messageType(message.getMessageType())
                            .createdTime(message.getCreatedTime())
                            .build()
            );
        }
        return response;
    }

    @Transactional
    public void createSimpleChannel(SimpleChannelRequest request) {
        Long memberId1
                = request.getMemberId1().compareTo(request.getMemberId2()) < 0
                ? request.getMemberId1() : request.getMemberId2();
        Long memberId2
                = request.getMemberId1().compareTo(request.getMemberId2()) > 0
                ? request.getMemberId1() : request.getMemberId2();
        String reference = uuidFactory.generateUUIDWithParam(memberId1 + "&" + memberId2);
        Channel channel = channelRepository.findByReference(reference);
        if (channel == null) {
            channel = channelRepository.save(Channel.builder()
                    .reference(reference)
                    .accessType(AccessType.PRIVATE)
                    .channelType(ChannelType.SIMPLE)
                    .build());
            channelMemberRepository.save(ChannelMember.builder()
                    .memberId(memberId1)
                    .reference(channel.getReference())
                    .build());
            channelMemberRepository.save(ChannelMember.builder()
                    .memberId(memberId2)
                    .reference(channel.getReference())
                    .build());
        }
    }

    @Transactional
    public void createMultipleChannel(MultipleChannelRequest request) {
        String reference = uuidFactory.generateUUIDWithoutParam();
        Channel channel = channelRepository.save(Channel.builder()
                .reference(reference)
                .accessType(request.getAccessType())
                .channelType(ChannelType.MULTIPLE)
                .build());
        channelMemberRepository.save(ChannelMember.builder()
                .memberId(request.getMemberId())
                .reference(channel.getReference())
                .build());
    }
}
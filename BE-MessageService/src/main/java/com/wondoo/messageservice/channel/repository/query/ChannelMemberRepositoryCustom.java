package com.wondoo.messageservice.channel.repository.query;

import java.util.List;

public interface ChannelMemberRepositoryCustom {
    Long findMemberIdByMemberIdAndReference(Long memberId, String reference);
    List<String> findByMemberId(Long memberId);
}

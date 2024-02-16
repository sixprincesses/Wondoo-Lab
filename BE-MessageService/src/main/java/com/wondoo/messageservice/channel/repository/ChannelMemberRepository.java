package com.wondoo.messageservice.channel.repository;

import com.wondoo.messageservice.channel.data.ChannelMember;
import com.wondoo.messageservice.channel.repository.query.ChannelMemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long>, ChannelMemberRepositoryCustom {
}

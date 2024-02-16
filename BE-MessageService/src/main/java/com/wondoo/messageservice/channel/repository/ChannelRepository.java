package com.wondoo.messageservice.channel.repository;

import com.wondoo.messageservice.channel.data.Channel;
import com.wondoo.messageservice.channel.repository.query.ChannelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom {
    Channel findByReference(String reference);
    List<Channel> findByReferenceIn(List<String> channelReferenceList);
}

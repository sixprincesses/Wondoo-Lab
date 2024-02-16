package com.wondoo.messageservice.message.repository;

import com.wondoo.messageservice.message.data.Message;
import com.wondoo.messageservice.message.repository.query.MessageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
    List<Message> findByReference(String reference);
}

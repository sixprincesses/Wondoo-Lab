package com.wondoo.messageservice.message.repository.query;

import com.wondoo.messageservice.message.data.Message;

public interface MessageRepositoryCustom {
    Message findLastMessageByReference(String reference);
}

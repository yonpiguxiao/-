package com.seven.job.service;


import com.seven.job.domain.message.Message;

import java.util.List;

public interface IMessageService {

    boolean batchInsert(List<Message> messageList);
}
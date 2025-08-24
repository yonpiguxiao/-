package com.seven.job.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.job.domain.message.Message;
import com.seven.job.mapper.message.MessageMapper;
import com.seven.job.service.IMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Override
    public boolean batchInsert(List<Message> messageList) {
        return saveBatch(messageList);
    }
}

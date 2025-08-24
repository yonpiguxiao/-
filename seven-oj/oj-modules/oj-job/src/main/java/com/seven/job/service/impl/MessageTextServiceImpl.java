package com.seven.job.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.job.domain.message.MessageText;
import com.seven.job.mapper.message.MessageTextMapper;
import com.seven.job.service.IMessageTextService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageTextServiceImpl extends ServiceImpl<MessageTextMapper, MessageText> implements IMessageTextService {
    @Override
    public boolean batchInsert(List<MessageText> messageTextList) {
        return saveBatch(messageTextList);
    }
}

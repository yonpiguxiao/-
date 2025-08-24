package com.seven.friend.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.common.core.constants.Constants;
import com.seven.common.core.domain.PageQueryDTO;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.common.core.enums.ExamListType;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.core.utils.ThreadLocalUtil;
import com.seven.common.redis.service.RedisService;
import com.seven.common.security.exception.ServiceException;
import com.seven.friend.domain.exam.vo.ExamVO;
import com.seven.friend.domain.message.Message;
import com.seven.friend.domain.message.MessageText;
import com.seven.friend.domain.message.vo.MessageTextVO;
import com.seven.friend.manager.MessageCacheManager;
import com.seven.friend.mapper.message.MessageMapper;
import com.seven.friend.mapper.message.MessageTextMapper;
import com.seven.friend.service.user.IUserMessageService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageServiceImpl implements IUserMessageService {

    @Autowired
    private MessageCacheManager messageCacheManager;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageTextMapper messageTextMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public TableDataInfo list(PageQueryDTO dto) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        Long total = messageCacheManager.getListSize(userId);
        List<MessageTextVO> messageTextVOList;
        if(total == null || total <= 0) {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
            messageTextVOList = messageTextMapper.selectUserMsgList(userId);
            messageCacheManager.refreshCache(userId);
            total = new PageInfo<>(messageTextVOList).getTotal();
        } else {
            messageTextVOList = messageCacheManager.getMsgTextVOList(dto, userId);
            total = messageCacheManager.getListSize(userId);
        }
        if(CollectionUtil.isEmpty(messageTextVOList)) {
            return TableDataInfo.empty();
        }
        return TableDataInfo.success(messageTextVOList, total);
    }

    @Override
    public int delete(Long textId) {
        MessageText messageText = messageTextMapper.selectById(textId);
        if(messageText == null) {
            throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        }
        messageCacheManager.deleteCache(textId);
        List<Message> messageList = messageMapper.selectList(new LambdaQueryWrapper<Message>()
                .select(Message::getMessageId)
                .eq(Message::getTextId, textId));
        for (Message message : messageList) {
            messageMapper.deleteById(message.getMessageId());
        }
        return messageTextMapper.deleteById(textId);
    }
}

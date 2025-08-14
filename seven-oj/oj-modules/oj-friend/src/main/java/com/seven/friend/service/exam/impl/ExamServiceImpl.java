package com.seven.friend.service.exam.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;
import com.seven.friend.domain.exam.vo.ExamVO;
import com.seven.friend.manager.ExamCacheManager;
import com.seven.friend.mapper.exam.ExamMapper;
import com.seven.friend.service.exam.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements IExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamCacheManager examCacheManager;

    @Override
    public List<ExamVO> list(ExamQueryDTO examQueryDTO) {
        PageHelper.startPage(examQueryDTO.getPageNum(), examQueryDTO.getPageSize());
        return examMapper.selectExamList(examQueryDTO);
    }

    @Override
    public TableDataInfo redisList(ExamQueryDTO examQueryDTO) {

        Long total = examCacheManager.getListSize(examQueryDTO.getType());
        List<ExamVO> examVOList;

        if(total == null || total == 0) {
            examVOList = list(examQueryDTO);
            examCacheManager.refreshCache(examQueryDTO.getType());
            total = new PageInfo<>(examVOList).getTotal();
        } else {

            examVOList = examCacheManager.getExamVOList(examQueryDTO);
            total = examCacheManager.getListSize(examQueryDTO.getType());
            if(examQueryDTO.getStartTime() != null && examQueryDTO.getEndTime() != null)
            {
                examVOList = list(examQueryDTO);
                total = new PageInfo<>(examVOList).getTotal();
            }
        }
        if(CollectionUtil.isEmpty(examVOList)) {
            return TableDataInfo.empty();
        }
        return TableDataInfo.success(examVOList, total);
    }
}

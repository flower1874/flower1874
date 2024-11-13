package com.oj.onlinejudge.service.impl;

import com.oj.onlinejudge.domain.po.Problems;
import com.oj.onlinejudge.mapper.ProblemsMapper;
import com.oj.onlinejudge.service.IProblemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Service
public class ProblemsServiceImpl extends ServiceImpl<ProblemsMapper, Problems> implements IProblemsService {

}

package com.oj.onlinejudge.service.impl;

import com.oj.onlinejudge.domain.po.Submissions;
import com.oj.onlinejudge.mapper.SubmissionsMapper;
import com.oj.onlinejudge.service.ISubmissionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提交表 服务实现类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Service
public class SubmissionsServiceImpl extends ServiceImpl<SubmissionsMapper, Submissions> implements ISubmissionsService {

}

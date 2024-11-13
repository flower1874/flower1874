package com.oj.onlinejudge.service.impl;

import com.oj.onlinejudge.domain.po.TestCases;
import com.oj.onlinejudge.mapper.TestCasesMapper;
import com.oj.onlinejudge.service.ITestCasesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用例表 服务实现类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Service
public class TestCasesServiceImpl extends ServiceImpl<TestCasesMapper, TestCases> implements ITestCasesService {

}

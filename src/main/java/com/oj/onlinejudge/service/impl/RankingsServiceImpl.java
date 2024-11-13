package com.oj.onlinejudge.service.impl;

import com.oj.onlinejudge.domain.po.Rankings;
import com.oj.onlinejudge.mapper.RankingsMapper;
import com.oj.onlinejudge.service.IRankingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 排名表 服务实现类
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Service
public class RankingsServiceImpl extends ServiceImpl<RankingsMapper, Rankings> implements IRankingsService {

}

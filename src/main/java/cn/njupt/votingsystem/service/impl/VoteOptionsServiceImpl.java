package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.repository.VoteOptionsRepository;
import cn.njupt.votingsystem.service.VoteOptionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Service
public class VoteOptionsServiceImpl implements VoteOptionsService {

    @Resource
    public VoteOptionsRepository voteOptionsRepository;

    @Override
    public void updateVotingCount(Integer optionId, Integer count) {
        voteOptionsRepository.updateCount(optionId, count);
    }
}

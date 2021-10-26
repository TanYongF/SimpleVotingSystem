package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.repository.VoteRepository;
import cn.njupt.votingsystem.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    @Resource
    private VoteRepository voteRepository;

    @Override
    public Vote save(Vote v) {
        log.error(v.toString());
        return voteRepository.save(v);
    }

    @Override
    public void deleteVoteById(Vote v) {
        Integer id = v.getId();
        voteRepository.deleteById(id);
    }

    @Override
    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }
}

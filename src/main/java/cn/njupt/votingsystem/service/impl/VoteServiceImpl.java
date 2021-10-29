package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.pojo.VoteOptions;
import cn.njupt.votingsystem.repository.VoteRepository;
import cn.njupt.votingsystem.service.RedisService;
import cn.njupt.votingsystem.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    @Resource
    private RedisService redisService;

    @Override
    public Vote save(Vote v) {
        Optional<Vote> old = voteRepository.findById(v.getId());
        HashMap<Integer, Integer> mp = new HashMap<>();
//        if(old.isPresent()){
//            for(VoteOptions vs : v.getVoteOptionsList()){
//                mp.put(vs.getId(), mp.getOrDefault(vs.getId() ,0 ) + 1);
//            }
//
//            for(VoteOptions vs : old.get().getVoteOptionsList()){
//                mp.put(vs.getId(), mp.getOrDefault(vs.getId(), 0 ) + 1);
//            }
//            for(VoteOptions oldVp : old.get().getVoteOptionsList()){
//                if(mp.get(oldVp.getId()) != 2) redisService.remove("option_" + oldVp.getId());
//            }
//            for(VoteOptions newVp : v.getVoteOptionsList()){
//
//            }
//        }
//        log.error(v.toString());
        Vote ret = voteRepository.save(v);
        for(VoteOptions vp : ret.getVoteOptionsList()){
            if(!redisService.exists("option_" + vp.getId())){
                redisService.set("option_" + vp.getId(), 0);
            }
        }
        return null;
    }

    @Override
    public void deleteVoteById(Integer id) {
        voteRepository.deleteById(id);
        redisService.remove("option_" + id);
    }

    @Override
    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public Vote findById(Integer id) {
        Optional<Vote> byId = voteRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public void vote() {

    }
}

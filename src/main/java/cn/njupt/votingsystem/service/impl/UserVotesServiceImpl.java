package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.model.UserVoteGroupDay;
import cn.njupt.votingsystem.pojo.UserVotes;
import cn.njupt.votingsystem.repository.UserVotesRepository;
import cn.njupt.votingsystem.service.UserVotesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/27
 **/
@Service
public class UserVotesServiceImpl implements UserVotesService {

    @Resource
    private UserVotesRepository userVotesRepository;

    @Override
    public boolean checkUser(String IP, String VID, Integer channelId) {
        //是否存在填写记录
        Boolean hasVisited = userVotesRepository.existsByVIDAndChannelId(VID, channelId);
        if (hasVisited) return false;

        //该IP是否已经超限
        Integer ipCounts = userVotesRepository.countByUserIPAndChannelId(IP, channelId);
        if (ipCounts > 10) return false;
        return true;
    }

    @Override
    public UserVotes save(UserVotes userVotes) {
        return userVotesRepository.save(userVotes);
    }

    @Override
    public List<UserVotes> findAllUserVotes(Pageable pageable) {
        Page<UserVotes> ret = userVotesRepository.findAll(pageable);
        return ret.getContent();
    }

    @Override
    public List<UserVoteGroupDay> calByDay() {
        return userVotesRepository.findNumsGroupByDay();
    }

    @Override
    public List<UserVoteGroupDay> calByDayAndChannelId(Integer id) {
        return userVotesRepository.findNumsGroupByDayAndChannelId(id);
    }

    @Override
    public List<UserVotes> getByVIDAndChannelId(String VID, Integer channelId) {
        return userVotesRepository.findByVIDAndChannelId(VID, channelId);
    }

    @Override
    public Integer getTodayVotesSum() {
        LocalDateTime start =  LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end =  LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return userVotesRepository.countByCreateAtBetween(start,end);
    }

    @Override
    public HashMap<String, Integer> getTodayResult() {
        HashMap<String, Integer> mp = new HashMap<>();
        LocalDateTime start =  LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end =  LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        //总投票数
        Integer VotingNum = userVotesRepository.getSumVotingNums();
        //今日单频道投票数
        Integer maxVotingNums = userVotesRepository.getMaxVotingNums(start, end);
        //今日投票数
        Integer totalVotingNum = userVotesRepository.countByCreateAtBetween(start, end);

        mp.put("voting_num", VotingNum);
        mp.put("max_voting_num", maxVotingNums);
        mp.put("total_voting_num", totalVotingNum);

        return mp;
    }

}

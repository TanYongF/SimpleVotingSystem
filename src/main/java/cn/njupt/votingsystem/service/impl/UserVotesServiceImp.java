package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.model.UserVoteGroupDay;
import cn.njupt.votingsystem.pojo.UserVotes;
import cn.njupt.votingsystem.repository.UserVotesRepository;
import cn.njupt.votingsystem.service.UserVotesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/27
 **/
@Service
public class UserVotesServiceImp implements UserVotesService {

    @Resource
    private UserVotesRepository userVotesRepository;

    @Override
    public boolean checkUser(String IP, String VID, Integer channelId) {
        Boolean hasVisited = userVotesRepository.existsByVIDAndChannelId(VID, channelId);
        if(hasVisited) return false;
        Integer ipCounts = userVotesRepository.countByUserIPAndChannelId(IP, channelId);
        if(ipCounts > 10) return false;
        return true;
    }

    @Override
    public UserVotes save(UserVotes userVotes) {
        return userVotesRepository.save(userVotes);
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
//        return null;
    }

}

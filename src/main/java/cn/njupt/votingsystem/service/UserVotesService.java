package cn.njupt.votingsystem.service;

import cn.njupt.votingsystem.model.UserVoteGroupDay;
import cn.njupt.votingsystem.pojo.UserVotes;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/27
 **/
public interface UserVotesService {
    boolean checkUser(String IP, String sessionId, Integer channelId);

    UserVotes save(UserVotes userVotes);

    List<UserVotes> findAllUserVotes(Pageable pageable);

    List<UserVoteGroupDay> calByDay();

    List<UserVoteGroupDay> calByDayAndChannelId(Integer id);

    List<UserVotes> getByVIDAndChannelId(String VID, Integer channelId);

    Integer getTodayVotesSum();

    HashMap<String, Integer> getTodayResult();
}

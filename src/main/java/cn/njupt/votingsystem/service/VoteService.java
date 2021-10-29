package cn.njupt.votingsystem.service;

import cn.njupt.votingsystem.pojo.Vote;

import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public interface VoteService {
    Vote save(Vote v);

    void deleteVoteById(Integer id);

    List<Vote> findAllVotes();

    Vote findById(Integer id);

    void vote();

}

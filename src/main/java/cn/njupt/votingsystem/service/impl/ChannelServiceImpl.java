package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.model.ChannelDTO;
import cn.njupt.votingsystem.pojo.Channel;
import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.pojo.VoteOptions;
import cn.njupt.votingsystem.repository.ChannelRepository;
import cn.njupt.votingsystem.repository.VoteRepository;
import cn.njupt.votingsystem.service.ChannelService;
import cn.njupt.votingsystem.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Service
public class ChannelServiceImpl implements ChannelService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private VoteRepository voteRepository;

    @Resource
    private RedisService redisService;

    @Override
    public Channel getById(Integer id) {
        Optional<Channel> byId = channelRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<ChannelDTO> findAllToChannelInfo() {
        return channelRepository.findAllToChannelInfo();
    }

    @Override
    @Transactional
    public Boolean deleteById(Integer id) {
        Channel byId = channelRepository.getById(id);
        redisService.remove("channel_"+ byId.getId());
        for(Vote vote : byId.getVotes()){
            int[] voteOptionsId = vote.getVoteOptionsList().stream().mapToInt(VoteOptions::getId).toArray();
            ArrayList keys = new ArrayList<String>();
            for(Integer idx : voteOptionsId) keys.add("options_" + idx);
            redisService.remove((String[])(keys.toArray(new String[0])));
        }
        channelRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional
    @Override
    public Channel save(Channel channel) {
        channel.setVotingNum(-1);
        Channel newChannel = channelRepository.save(channel);
        redisService.set("channel_" + newChannel.getId(), 0);
        for(Vote vote : newChannel.getVotes()){
            int[] voteOptionsIds =
                     vote.getVoteOptionsList().stream().mapToInt(VoteOptions::getId).toArray();
            ArrayList<String> keys = new ArrayList<String>();
            for(Integer id : voteOptionsIds) keys.add("options_" + id);
            redisService.add((String[]) keys.toArray(new String[0]));
        }
        return newChannel;
    }



    @Override
    public Channel writeBack(Integer channelId) {
        Channel old = channelRepository.getById(channelId);
        Integer peopleNum = (Integer) redisService.get("channel_" + old.getId());
        redisService.remove("channel_" + old.getId());
        old.setVotingNum(peopleNum);
        for(int i = 0; i < old.getVotes().size(); i++){
            Vote vote = old.getVotes().get(i);
            for(int j = 0 ; j < vote.getVoteOptionsList().size(); j++){
                VoteOptions voteOptions = vote.getVoteOptionsList().get(j);
                int singeNum = (int)redisService.get("options_" + voteOptions.getId());
                voteOptions.setTotalVoting(singeNum);
                redisService.remove("options_" + voteOptions.getId());
            }
        }
        channelRepository.save(old);
        return old;
    }

    @Override
    public Channel update(Channel channel) {
        Channel newChannel = channelRepository.save(channel);
        for(Vote vote : newChannel.getVotes()){
            for(VoteOptions voteOptions : vote.getVoteOptionsList()){
                if(!redisService.exists("options_" + voteOptions.getId())){
                    redisService.add("option_" + voteOptions.getId());
                }
            }
        }
        return newChannel;
    }
}

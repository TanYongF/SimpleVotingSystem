package cn.njupt.votingsystem.service.impl;

import cn.njupt.votingsystem.pojo.Channel;
import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.pojo.VoteOptions;
import cn.njupt.votingsystem.repository.ChannelRepository;
import cn.njupt.votingsystem.service.ChannelService;
import cn.njupt.votingsystem.service.RedisService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public class ChannelServiceImpl implements ChannelService {

    @Resource
    private ChannelRepository channelRepository;

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
    public Boolean deleteById(Integer id) {
        Channel byId = channelRepository.getById(id);
        redisService.remove("channel_"+ byId.getId());
        for(Vote vote : byId.getVotes()){
            redisService.set("vote_" + vote.getId(), 0);
//            vote.getVoteOptionsList().stream().

        }
        channelRepository.deleteById(id);

        return Boolean.TRUE;
    }

    @Override
    public Channel save(Channel channel) {
        Channel newChannel = channelRepository.save(channel);
        redisService.set("channel_" + newChannel.getId(), 0);
        for(Vote vote : channel.getVotes()){
            redisService.set("vote_", vote.getId());
            Integer[] voteOptionsIds = (Integer[]) vote.getVoteOptionsList().stream().map(VoteOptions::getId).toArray();
            ArrayList<String> keys = new ArrayList<>();
            for(Integer id : voteOptionsIds) keys.add("options_" + id);
            redisService.add((String[]) keys.toArray());
        }
        return newChannel;
    }
}

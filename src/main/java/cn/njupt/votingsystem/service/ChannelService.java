package cn.njupt.votingsystem.service;

import cn.njupt.votingsystem.model.ChannelDTO;
import cn.njupt.votingsystem.pojo.Channel;

import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public interface ChannelService  {

    Channel getById(Integer id);

    List<Channel> findAll();

    List<ChannelDTO> findAllToChannelInfo();

    Boolean deleteById(Integer id);

    Channel save(Channel channel);

    Channel writeBack(Integer channelId);

    Channel update(Channel channel);
}

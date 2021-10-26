package cn.njupt.votingsystem.service;

import cn.njupt.votingsystem.pojo.Channel;
import cn.njupt.votingsystem.repository.ChannelRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public interface ChannelService  {

    Channel getById(Integer id);

    List<Channel> findAll();

    Boolean deleteById(Integer id);

    Channel save(Channel channel);
}

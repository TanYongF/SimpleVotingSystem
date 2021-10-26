package cn.njupt.votingsystem.controller;

import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.pojo.Channel;
import cn.njupt.votingsystem.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
@RequestMapping("/channel")
public class ChannelController {

    @Resource
    private ChannelService channelService;

    /*根据id获取频道*/
    @GetMapping("/{channelId}")
    public RestResult<Channel> get(@PathVariable Integer channelId){
        return RestResult.success(200, new Channel());
    }
    /*获取全部频道*/
    @GetMapping("/")
    public RestResult<List<Channel>> getAll(){
        return RestResult.success(200, new ArrayList<Channel>());
    }
    /*增加频道*/
    @PostMapping("/")
    public RestResult<Channel> save(@RequestBody Channel channel){
        return RestResult.success(200, new Channel());
    }
    /*删除频道*/
    @DeleteMapping("/{channelId}")
    public RestResult<Boolean> delete(@PathVariable Integer channelId){
        return RestResult.success(200, Boolean.FALSE);
    }


}

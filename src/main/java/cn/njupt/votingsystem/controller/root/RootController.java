package cn.njupt.votingsystem.controller.root;

import cn.hutool.json.JSONUtil;
import cn.njupt.votingsystem.model.ChannelDTO;
import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.model.UserDetail;
import cn.njupt.votingsystem.pojo.Channel;
import cn.njupt.votingsystem.service.ChannelService;
import cn.njupt.votingsystem.service.RedisService;
import cn.njupt.votingsystem.service.UserVotesService;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/27
 **/
@Controller
//@Secured("")
@RequestMapping("/root")
public class RootController {

    @Resource
    private RedisService redisService;

    @Resource
    private ChannelService channelService;

    @Resource
    private UserVotesService userVotesService;


    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/channel")
    public String getChannels(Model model) {
        List<ChannelDTO> channels = channelService.findAllToChannelInfo();
        List<Integer> voteNums = new ArrayList<>();
        for (int i = 0; i < channels.size(); i++) {
            int num = (int) redisService.get("channel_" + channels.get(i).getId());
            channels.get(i).setVotingNum(num);
        }
        model.addAttribute("channels", channels);
        return "admin/channels";
    }

    @GetMapping("/channel/{channelId}")
    public String getChannel(@PathVariable Integer channelId, Model model) {

        Channel channel = channelService.getById(channelId);
        Integer votingNums = (Integer) redisService.get("channel_" + channel.getId());
        channel.setVotingNum(votingNums);
        model.addAttribute("channel", channel);
        model.addAttribute("channelJson", JSON.toJSONStringWithDateFormat(channel, JSON.DEFFAULT_DATE_FORMAT));
        return "admin/channel";
    }

    /*增加频道*/
    @PostMapping("/channel")
    @ResponseBody
    public RestResult<Integer> save(@RequestBody Channel channel) {
        Channel save = channelService.save(channel);
        return RestResult.success(200, save.getId());
    }

    /*只更新频道名称*/
    @PutMapping("/channel/info")
    @ResponseBody
    public RestResult<Boolean> updateInfo(@RequestBody Channel channel) {
        Channel ret = channelService.getById(channel.getId());
        ret.setInfo(channel.getInfo());
        ret.setTitle(channel.getTitle());
        ret.setStartTime(channel.getStartTime());
        ret.setEndTime(channel.getEndTime());
        channelService.update(ret);
        return RestResult.success(200, Boolean.TRUE);
    }

    /*更新频道*/
    @PutMapping("/channel")
    @ResponseBody
    public RestResult<Channel> update(@RequestBody Channel channel) {
        Channel update = channelService.update(channel);
        return RestResult.success(200, channel);
    }


    /*删除频道*/
    @DeleteMapping("/channel/{channelId}")
    @ResponseBody
    public RestResult<Boolean> delete(@PathVariable Integer channelId) {
        Boolean ret = channelService.deleteById(channelId);
        return RestResult.success(200, ret);
    }

    /*获得频道结果，结果展示*/
    @GetMapping("/channel/result/{channelId}")
    public String getResult(@PathVariable Integer channelId, Model model) {
        Channel channel = channelService.getById(channelId);
//        List<UserVoteGroupDay> userV = userVotesService.calByDayAndChannelId(channelId);
        for (int i = 0; i < channel.getVotes().size(); i++) {
            for (int j = 0; j < channel.getVotes().get(i).getVoteOptionsList().size(); j++) {
                Integer num =
                        (Integer) redisService.get("option_" + channel.getVotes().get(i).getVoteOptionsList().get(j).getId());
                channel.getVotes().get(i).getVoteOptionsList().get(j).setTotalVoting(num);
            }
        }


        model.addAttribute("channel", JSONUtil.toJsonStr(channel));
        return "admin/channelResult";
    }


    /*获得用户信息*/
    @GetMapping("/info")
    public String getInfo(Authentication authentication, Model model) {
        UserDetail principal = (UserDetail) authentication.getPrincipal();
        model.addAttribute("userInfo", principal);
        return "admin/info";
    }
}

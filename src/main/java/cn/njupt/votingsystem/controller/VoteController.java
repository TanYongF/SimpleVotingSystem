package cn.njupt.votingsystem.controller;

import cn.hutool.json.JSONUtil;
import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.pojo.UserVotes;
import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.service.RedisService;
import cn.njupt.votingsystem.service.UserVotesService;
import cn.njupt.votingsystem.service.VoteOptionsService;
import cn.njupt.votingsystem.service.VoteService;
import cn.njupt.votingsystem.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
@Slf4j
public class VoteController {

    @Resource
    private VoteService voteService;

    @Resource
    private RedisService redisService;

    @Resource
    private UserVotesService userVotesService;

    @Resource
    private VoteOptionsService voteOptionsService;


    @ResponseBody
    @GetMapping("/vote")
    public List<Vote> getVotes() {
        return voteService.findAllVotes();
    }

    @PostMapping("/vote")
    @ResponseBody

    public RestResult<Boolean> saveVotes(@RequestBody List<Integer> votes,
                                         @RequestParam(name = "channel_id") Integer channelId,
                                         HttpServletRequest request,
                                         @CookieValue("VID") String VID,
                                         @CookieValue("optionId") String optionId,
                                         HttpServletResponse response) {

        String ipAddress = IPUtil.getIpAddress(request);

        //将生成的VID和其进行比对
        String trueOption = (String) redisService.get(VID);
        if (!trueOption.equals(optionId)) {
            return RestResult.error(400, Boolean.FALSE);
        }

        //频道投票数加1
//        redisService.plus(RedisService.REDIS_CHANNEL_PREFIX + channelId);
        //投票项每一项加1
        for (Integer optionsId : votes) redisService.plus(RedisService.REDIS_OPTION_PREFIX + optionsId);
        //更新热榜
        redisService.alterHotList(RedisService.REDIS_HOT_LIST, channelId.toString());






        //redis清除
        redisService.remove(VID);

        //记录事件
        UserVotes userVotes = new UserVotes(ipAddress, VID, channelId, JSONUtil.toJsonStr(votes));
        userVotesService.save(userVotes);

        return RestResult.success(200, Boolean.TRUE);
    }

}

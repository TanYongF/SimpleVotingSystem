package cn.njupt.votingsystem.controller.root;

import cn.hutool.json.JSONUtil;
import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.repository.VoteOptionsRepository;
import cn.njupt.votingsystem.service.RedisService;
import cn.njupt.votingsystem.service.VoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
@RequestMapping("/root/vote")
public class RootVoteController {
    @Resource
    private VoteOptionsRepository voteOptionsRepository;

    @Resource
    private VoteService voteService;

    @Resource
    private RedisService redisService;

    @GetMapping("/{voteId}")
    @ResponseBody
    public RestResult<Vote> get(@PathVariable Integer voteId) {
        Vote ret = voteService.findById(voteId);
        String votesJson = JSONUtil.toJsonStr(ret);
        return RestResult.success(200, votesJson);
    }
//

    @DeleteMapping("/{voteId}")
    @ResponseBody
    public RestResult<Boolean> deleteVote(@PathVariable Integer voteId) {
        Vote vote = voteService.findById(voteId);
        voteService.deleteVoteById(voteId);
        redisService.removeVote(vote);
        return RestResult.success(200, Boolean.TRUE);
    }

}

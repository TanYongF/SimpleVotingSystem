package cn.njupt.votingsystem.controller;

import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.service.VoteService;
import cn.njupt.votingsystem.service.impl.VoteServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
public class VoteController {

    @Resource
    private VoteService voteService;

    @ResponseBody
    @GetMapping("/vote")
    public List<Vote> getVotes(){
        return voteService.findAllVotes();
    }

    @PostMapping("/vote")
    public RestResult<Vote> saveVotes(@RequestBody  Vote vote){
        Vote save = voteService.save(vote);
        return RestResult.success(200, save);
    }

}

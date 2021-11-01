package cn.njupt.votingsystem.controller.root;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.model.UserVoteGroupDay;
import cn.njupt.votingsystem.pojo.UserVotes;
import cn.njupt.votingsystem.service.UserVotesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/11/1
 **/
@Controller
public class VotesDataController {

    @Resource
    private UserVotesService userVotesService;

    @GetMapping("/result/today")
    @ResponseBody
    public RestResult<HashMap<String, Integer>> getTodayVoting(){
        HashMap<String, Integer> result = userVotesService.getTodayResult();
        return RestResult.success(200, result);
    }

    @GetMapping("/result/votingNums/today/{channel}")
    @ResponseBody
    public RestResult<Integer> getTodayVotingByChannelId(@PathVariable Integer channelId, @PathVariable String channel){
        return null;
    }


    @GetMapping("/result/trend/")
    @ResponseBody
    public RestResult getRecentNums(){
        HashMap<String, Long> mp = new HashMap<>();
        List<UserVoteGroupDay> userVoteGroupDays = userVotesService.calByDay();
        Date curDate = new Date();
        //初始化近一个月所有的时间
        for(int i = 0; i < 30; i++){
            curDate = DateUtil.offset(curDate, DateField.DAY_OF_MONTH, -1);
            mp.put(DateUtil.format(curDate, "yyyy/MM/dd"), 0L);
        }
        for(UserVoteGroupDay groupDay : userVoteGroupDays){
            mp.put(DateUtil.format(groupDay.getTime(), "yyyy/MM/dd"), groupDay.getNums());
        }
        return RestResult.success(200, JSONUtil.toJsonStr(mp));
    }
    @GetMapping("/result/trend/{channelId}")
    @ResponseBody
    public RestResult getRecentNumsByChannelId(@PathVariable Integer channelId){
        HashMap<String, Long> mp = new HashMap<>();
        List<UserVoteGroupDay> userVoteGroupDays = userVotesService.calByDayAndChannelId(channelId);
        Date curDate = new Date();
        //初始化近一个月所有的时间
        for(int i = 0; i < 30; i++){
            curDate = DateUtil.offset(curDate, DateField.DAY_OF_MONTH, -1);
            mp.put(DateUtil.format(curDate, "yyyy/MM/dd"), 0L);
        }
        for(UserVoteGroupDay groupDay : userVoteGroupDays){
            mp.put(DateUtil.format(groupDay.getTime(), "yyyy/MM/dd"), groupDay.getNums());
        }
        return RestResult.success(200, JSONUtil.toJsonStr(mp));
    }


    @GetMapping("/result/record")
    @ResponseBody
    public RestResult<List<UserVotes>> get(){
        Sort.Direction sort = Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(0, 50, sort, "createAt");
        List<UserVotes> ret = userVotesService.findAllUserVotes(pageable);
        return RestResult.success(200, ret);
    }

}

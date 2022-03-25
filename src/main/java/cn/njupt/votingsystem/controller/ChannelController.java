package cn.njupt.votingsystem.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.njupt.votingsystem.model.ChannelDTO;
import cn.njupt.votingsystem.pojo.Channel;
import cn.njupt.votingsystem.pojo.UserVotes;
import cn.njupt.votingsystem.service.ChannelService;
import cn.njupt.votingsystem.service.RedisService;
import cn.njupt.votingsystem.service.UserVotesService;
import cn.njupt.votingsystem.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Describe: 频道类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
@RequestMapping("/channel")
@Slf4j
public class ChannelController {

    @Resource
    private ChannelService channelService;

    @Resource
    private RedisService redisService;

    @Resource(name = "userVotesServiceImpl")
    private UserVotesService userVotesService;

    /*根据id获取频道*/
    @GetMapping("/{channelId}")
    @Transactional
    public String get(@PathVariable Integer channelId,
                      @CookieValue(value = "VID", required = false) String VID,
                      Model model,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        Channel channel = channelService.getById(channelId);
        String ip = IPUtil.getIpAddress(request);

        boolean canVote = true;
        //如果不存在VID那么就创建，存在就会查找是否可以填写
        if (VID == null) {
            //生成校验码，只有客户端拿到并和服务端redis数据库进行比对那才能投票
            String uuid = IdUtil.simpleUUID();
            VID = uuid;
            Cookie cookie = new Cookie("VID", VID);
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24 * 365 * 3);
            response.addCookie(cookie);
        } else {
            canVote = userVotesService.checkUser(ip, VID, channelId);
        }

        //向客户端设置一个用于判别投票的optionId;
        Cookie optionCookie = new Cookie("optionId", IdUtil.simpleUUID());
        optionCookie.setPath("/vote");
        response.addCookie(optionCookie);

        if (canVote) {
            //如果可投票，设置一个20分钟的key
            redisService.setString(VID, optionCookie.getValue(), 20 * 60);
            model.addAttribute("canVote", 0);
        } else {
            List<UserVotes> records = userVotesService.getByVIDAndChannelId(VID, channelId); //获取投票信息
            if (records.size() == 0) model.addAttribute("canVote", 1); //不可投票，IP数量超限
            else {
                model.addAttribute("canVote", 2); //不可投票，已经填写过投票数据
                model.addAttribute("answers", JSONUtil.toJsonStr(records.get(0).getVotes()));
            }
        }
        model.addAttribute("votes", channel);
        model.addAttribute("allVotingNums", redisService.get("channel_" + channelId));
        return "channel";
    }

    /*获取全部频道*/
    @GetMapping("")
    public String getAll(Model model) {

        Set<String> channels = redisService.zGetTopN(RedisService.REDIS_HOT_LIST, -1);
        Set<Integer> hotList = redisService.zGetTopN(RedisService.REDIS_HOT_LIST, 2);

        List<Integer>  allList = channels.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<ChannelDTO> allToChannelDTO = channelService.findAllToChannelInfo(allList);
        for (ChannelDTO channelDTO : allToChannelDTO) {
            channelDTO.setVotingNum(redisService.zGetScore(RedisService.REDIS_HOT_LIST, String.valueOf(channelDTO.getId())));
        }
        model.addAttribute("channels", allToChannelDTO);
        return "channels";
    }


}

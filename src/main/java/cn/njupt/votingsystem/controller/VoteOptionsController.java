package cn.njupt.votingsystem.controller;

import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.pojo.VoteOptions;
import cn.njupt.votingsystem.repository.VoteOptionsRepository;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
@RequestMapping("/options")
public class VoteOptionsController {
    @Resource
    private VoteOptionsRepository voteOptionsRepository;

    @GetMapping("/{optionId}")
    public RestResult<VoteOptions> get(@PathVariable Integer optionId){
        Optional<VoteOptions> optionById = voteOptionsRepository.findById(optionId);
        if (optionById.isPresent()){
            return RestResult.success(200, optionById.get());
        }else{
            return RestResult.error(400, "未找到!");
        }
    }

//    @PostMapping("/{optionId}")
//    @CachePut(value = "options", key = "#optionId")
//    public  RestResult<VoteOptions> save(@PathVariable Integer optionId){
////        return RestResult
//    }
//
//    public RestResult<Boolean> voteOp


}

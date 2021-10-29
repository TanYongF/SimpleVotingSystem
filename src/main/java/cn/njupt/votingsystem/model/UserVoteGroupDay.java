package cn.njupt.votingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/29
 **/
@Data
@AllArgsConstructor
public class UserVoteGroupDay {
    private LocalDateTime time;
    private Integer nums;
    private Integer channelId;
}

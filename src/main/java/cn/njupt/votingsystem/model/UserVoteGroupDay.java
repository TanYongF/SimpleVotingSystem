package cn.njupt.votingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/29
 **/
@Data
public class UserVoteGroupDay {

    private Date time;

    private Long nums;

    private Integer channelId;

    public UserVoteGroupDay(Date time, Long nums, Integer channelId) {
        this.time = time;
        this.nums = nums;
        this.channelId = channelId;
    }
}

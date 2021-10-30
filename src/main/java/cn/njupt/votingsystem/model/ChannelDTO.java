package cn.njupt.votingsystem.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/26
 **/
@Data

public class ChannelDTO implements Serializable {

    private Integer id;

    /*
     * 频道名称
     * */
    private String title;

    /*
     * 频道简介
     * */
    private String info;

    /*
     * 总投票人数
     * */
    private Integer votingNum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ChannelDTO() {
    }

    public ChannelDTO(Integer id, String title, String info, Integer votingNum, LocalDateTime creatAt,
                      LocalDateTime updateAt) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.votingNum = votingNum;
        this.startTime = creatAt;
        this.endTime = updateAt;
    }

    public ChannelDTO(String title) {
        this.title = title;
    }
}

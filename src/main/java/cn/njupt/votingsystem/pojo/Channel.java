package cn.njupt.votingsystem.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Data
@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

     /*
     * 频道下投票实体
     * */
    @OneToMany(targetEntity = Vote.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private List<Vote> votes;

    @CreatedDate
    private LocalDateTime creatAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}

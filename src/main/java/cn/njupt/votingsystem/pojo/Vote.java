package cn.njupt.votingsystem.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Entity
@Data
public class Vote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity = VoteOptions.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "vote_id")
    private List<VoteOptions> voteOptionsList;

    private String title;

    private Integer totalVoting;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}

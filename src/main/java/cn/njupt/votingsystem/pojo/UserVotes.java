package cn.njupt.votingsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Describe: 用户投票信息类
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userIP;

    public String VID;

    public Integer channelId;

    public String votes;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public UserVotes(String userIP, String VID, Integer channelId, String votes){
        this.userIP=userIP;
        this.VID=VID;
        this.channelId = channelId;
        this.votes = votes;
    }
}

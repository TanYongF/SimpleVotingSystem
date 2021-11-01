package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.model.UserVoteGroupDay;
import cn.njupt.votingsystem.pojo.User;
import cn.njupt.votingsystem.pojo.UserVotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserVotesRepository extends JpaRepository<UserVotes, Integer> {
    /*判断同一VID和频道是否存在*/
    Boolean existsByVIDAndChannelId(String VID, Integer channelId);

    /*根据用户IP判断用户投票次数*/
    Integer countByUserIPAndChannelId(String IP, Integer channelId);

    @Override
    <S extends UserVotes> S save(S entity);

    /*查看所有的投票趋势*/
    @Query(value =
            "select new cn.njupt.votingsystem.model.UserVoteGroupDay(date(uv.createAt), count(uv.channelId), uv.channelId)" +
                    "from UserVotes uv "+
                    "group by date(uv.createAt)"
    )
    List<UserVoteGroupDay> findNumsGroupByDay();

    /*查询频道的投票趋势，按照时间返回*/
    @Query(value =
            "select new cn.njupt.votingsystem.model.UserVoteGroupDay(date(uv.createAt), count(uv.channelId), uv.channelId)" +
                    "from UserVotes uv "+
                    "where uv.channelId = ?1 " +
                    "group by date(uv.createAt)"
    )
    List<UserVoteGroupDay> findNumsGroupByDayAndChannelId(Integer channelId);

    /*查询投票的总数*/
    @Query("select count(uv.id) from UserVotes uv")
    Integer  getSumVotingNums();

    /*查询一段时间内最高投票频道*/
    @Query(value = "select count(id) as num from user_votes where create_at between ?1 and ?2 group by channel_id" +
            " order by num desc limit 1", nativeQuery = true)
    Integer getMaxVotingNums(LocalDateTime startTime, LocalDateTime endTime);

    /*通过VID和ChannelID查询*/
    List<UserVotes> findByVIDAndChannelId(String VID, Integer ChannelId);

    @Override
    Page<UserVotes> findAll(Pageable pageable);

    //查询今日的总投票量
    Integer countByCreateAtBetween(LocalDateTime startTime, LocalDateTime endTime);

    /*查询频道号的今日浏览量*/
    Integer countByChannelIdAndCreateAtBetween(Integer channelId, LocalDateTime start, LocalDateTime endTiem);




}

package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.model.UserVoteGroupDay;
import cn.njupt.votingsystem.pojo.UserVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserVotesRepository extends JpaRepository<UserVotes, Integer> {
    Boolean existsByVIDAndChannelId(String VID, Integer channelId);

    Integer countByUserIPAndChannelId(String IP, Integer channelId);

    @Override
    <S extends UserVotes> S save(S entity);

    @Query(value =
            "select new cn.njupt.votingsystem.model.UserVoteGroupDay(uv.createAt, count(uv.VID), uv.channelId)" +
                    "from UserVotes uv" +
                    "group by  month(uv.createAt), year(uv.createAt), day(createAt)", nativeQuery = true
            )
    List<UserVoteGroupDay> findNumsGroupByDay();

    @Query(value =
            "select new cn.njupt.votingsystem.model.UserVoteGroupDay(uv.createAt, count(uv.VID), uv.channelId)" +
                    "from UserVotes uv" +
                    "where uv.channelId = :channelId" +
                    "group by  month(uv.createAt), year(uv.createAt), day(createAt)", nativeQuery = true
    )
    List<UserVoteGroupDay> findNumsGroupByDayAndChannelId(Integer channelId);

    List<UserVotes> findByVIDAndChannelId(String VID, Integer ChannelId);

}

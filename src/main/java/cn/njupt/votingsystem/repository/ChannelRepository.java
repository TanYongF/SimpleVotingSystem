package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.model.ChannelDTO;
import cn.njupt.votingsystem.pojo.Channel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    @Override
    Optional<Channel> findById(Integer integer);

    @Transactional
    @Override
    <S extends Channel> S save(S entity);

    @Transactional
    @Override
    void deleteById(Integer integer);

    @Query(value =
            "select new cn.njupt.votingsystem.model.ChannelDTO(c.id, c.title, c.info, c.votingNum, c.startTime, " +
                    "c.endTime) " +
                    "from Channel c WHERE id in :list")
    List<ChannelDTO> findAllToChannelInfo(@Param("list") List<Integer> list);


    @Query(value =
            "select new cn.njupt.votingsystem.model.ChannelDTO(c.id, c.title, c.info, c.votingNum, c.startTime, " +
                    "c.endTime) " +
                    "from Channel c")
    List<ChannelDTO> findAllToChannelInfo();
}

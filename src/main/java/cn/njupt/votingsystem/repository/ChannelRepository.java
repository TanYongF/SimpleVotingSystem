package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.pojo.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public interface  ChannelRepository extends JpaRepository<Channel, Integer> {
    @Override
    Optional<Channel> findById(Integer integer);

    @Override
    <S extends Channel> S save(S entity);

    @Override
    void deleteById(Integer integer);

    @Query(value = "SELECT id, title, info, voting_num FROM channel" , nativeQuery = true)
    List<Channel> findAll();
}

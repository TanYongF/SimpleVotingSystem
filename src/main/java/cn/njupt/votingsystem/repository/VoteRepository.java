package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.pojo.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Vote save(Vote vote);

    @Override
    List<Vote> findAll();

    @Override
    void deleteById(Integer integer);


}

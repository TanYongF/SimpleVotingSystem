package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.pojo.VoteOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteOptionsRepository extends JpaRepository<VoteOptions, Integer> {
    @Override
    Optional<VoteOptions> findById(Integer integer);

    VoteOptions save(VoteOptions voteOptions);
}

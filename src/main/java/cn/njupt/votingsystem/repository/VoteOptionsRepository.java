package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.pojo.VoteOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteOptionsRepository extends JpaRepository<VoteOptions, Integer> {
    @Override
    Optional<VoteOptions> findById(Integer integer);

    VoteOptions save(VoteOptions voteOptions);

    @Modifying
    @Query("update VoteOptions op set op.totalVoting = ?2 where op.id = ?1")
    int updateCount(Integer optionId, Integer count);
}

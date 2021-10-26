package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.pojo.UserVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVotesRepository extends JpaRepository<UserVotes, Integer> {
}

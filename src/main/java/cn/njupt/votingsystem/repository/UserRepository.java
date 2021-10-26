package cn.njupt.votingsystem.repository;

import cn.njupt.votingsystem.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public interface UserRepository extends JpaRepository<User, Integer> {
//    findByUsername(String userName);

    User findByUserName(String userName);
}

package com.daviaNhat.osahaneat.repository;

import com.daviaNhat.osahaneat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    /*
    select * from users where username = ? and password = ?
     */
    List<Users> findByUserNameAndPassword(String username, String password);
    Users findByUserName(String userName);
}

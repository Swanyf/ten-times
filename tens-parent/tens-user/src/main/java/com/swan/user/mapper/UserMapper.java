package com.swan.user.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.user.bean.User;

public interface UserMapper extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByNickname(String nickname);

}

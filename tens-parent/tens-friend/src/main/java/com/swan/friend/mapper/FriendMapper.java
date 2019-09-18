package com.swan.friend.mapper;

import com.swan.friend.bean.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendMapper extends JpaRepository<Friend, String> {

}

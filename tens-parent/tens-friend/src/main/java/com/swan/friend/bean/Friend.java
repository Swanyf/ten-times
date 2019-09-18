package com.swan.friend.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_friend")
@IdClass(Friend.class)
@Data
public class Friend implements Serializable {

    @Id
    private String userid;
    @Id
    private String friendid;

}
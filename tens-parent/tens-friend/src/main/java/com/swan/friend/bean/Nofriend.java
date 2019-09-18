package com.swan.friend.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_nofriend")
@Data
public class Nofriend implements Serializable {

    @Id
    private String userid;//
    private String friendid;//

}

package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *我的投票：(MyVote)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "MyVote")
public class MyVote implements Serializable {

    //MyVote编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_vote_id")
    private Integer my_vote_id;
   // 用户
   @Basic
    private Integer user;
   // 投票话题
   @Basic
    private String voting_topic;
   // 投票类型
   @Basic
    private String voting_type;
   // 投票数
   @Basic
    private String number_of_votes;
   // 投票说明
   @Basic
    private String voting_instructions;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}

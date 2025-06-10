package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *参与投票：(Vote)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "Vote")
public class Vote implements Serializable {

    //Vote编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Integer vote_id;
   // 投票话题
   @Basic
    private String voting_topic;
   // 投票类型
   @Basic
    private String voting_type;
   // 备注
   @Basic
    private String remarks;
   // 投票封面
   @Basic
    private String voting_cover;
   // 总票数
   @Basic
    private Integer votes_;
   // 话题介绍
   @Basic
    private String topic_introduction;
   // 投票说明
   @Basic
    private String voting_instructions;
    // 点击数
    @Basic
    private Integer hits;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}

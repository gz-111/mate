package com.cnsmash.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TournamentDetailVo {

    private Long id;

    /**
     * 比赛名称
     */
    private String name;

    /**
     * 报名开始时间
     */
    private Timestamp registerTime;

    /**
     * 比赛开始时间
     */
    private Timestamp startTime;

    /**
     * 比赛详情
     */
    private String detail;

    /**
     * 初始ban图
     */
    private String banStarter;

    /**
     * 初始ban图数量
     */
    private Integer banStarterCount;

    /**
     * 通用ban图
     */
    private String banCounter;

    /**
     * 通用ban图数量
     */
    private Integer banCounterCount;

    /**
     * 直播地址
     */
    private String live;

    /**
     * 分数门槛下限
     */
    private Integer pointLow;

    /**
     * 分数门槛上限
     */
    private Integer pointHigh;

    /**
     * 人数上限
     */
    private Integer playerLimit;

    /**
     * 是否有线限定（0否 1是）
     */
    private Integer wire;

    /**
     * 限制使用的加速节点
     */
    private String server;

    /**
     * 比赛状态
     */
    private String status;

    /**
     * 举办人ID
     */
    private Long host;

    /**
     * 举办人TAG
     */
    private String hostName;

    /**
     * 举办人头像
     */
    private String hostHead;

    /**
     * 报名人数
     */
    private Integer registerCount;

}

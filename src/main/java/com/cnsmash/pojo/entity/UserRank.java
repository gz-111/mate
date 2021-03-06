package com.cnsmash.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户排名
 *
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserRank extends BaseEntity {

    private Long id;

    /**
     * 用户id {@link User#getId()}
     */
    private Long userId;

    /**
     * 赛季
     */
    @TableField(value = "`quarter`")
    private String quarter;

    /**
     * 排名
     */
    @TableField(value = "`rank`")
    private Long rank;

    /**
     * 排名分数
     */
    private Long score;

    /**
     * 胜局
     */
    private Integer win;

    /**
     * 败局
     */
    private Integer lost;

    /**
     * 总对局
     */
    private Integer total;

    /**
     * 状态 0 正常 1 禁止
     */
    private Integer status;
}

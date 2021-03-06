package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.entity.UploadFile;
import lombok.Data;

import java.util.List;

/**
 * @author guanhuan_li
 */
@Data
public class UpdateUserInfoRo {

    private String name;

    private String mail;

    private Integer sex;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像 {@link UploadFile#getId()}
     */
    private Long head;

    private String code;

    /**
     * 队伍
     */
    private Long teamId;

    /**
     * 标签
     * List<String> json格式
     */
    private List<String> tags;

    /**
     * 自我介绍
     */
    private String intro;

    /**
     * 链接方式
     * 1.有线 2.无线
     */
    private Integer linkType;
}

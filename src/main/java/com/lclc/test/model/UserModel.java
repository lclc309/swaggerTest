package com.lclc.test.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户的实体类
 *
 * @author lichao 2017年1月4日
 */
@ApiModel("UserModel")
public class UserModel {

    // 用户id
    @ApiModelProperty(value = "用户id")
    private Long userid;

    // 用户名
    @ApiModelProperty(value = "用户名称")
    private String name;

    public Long getUserid() {

        return userid;
    }

    public void setUserid(Long userid) {

        this.userid = userid;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

}

package com.example.rommall.domain.vo;

import lombok.Data;

@Data
public class UserVo {
    private Integer id;
    private String phone;
    private String nickName;
    private Integer isadmin;
}

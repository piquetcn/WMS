package com.piquet.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    @NotNull
    private Integer id;                 //主键ID
    private String username;            //用户名
    @JsonIgnore
    private String password;            //密码
    private String role;                //身份
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;            //昵称
    @Email
    private String email;               //邮箱
    private String userPic;             //用户头像地址
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;   //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;   //更新时间
    private Boolean state;              //用户状态[0:停用|1:正常]
}

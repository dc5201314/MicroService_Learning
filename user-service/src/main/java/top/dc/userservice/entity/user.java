package top.dc.userservice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user")
public class user {
    @TableId
    private Integer id;
    private String mobile;
    private String password;
    private String userName;
    private String avatarUrl;
    private Date createTime;
    private Date updateTime;
}
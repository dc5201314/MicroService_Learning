package top.dc.requestservice.entity;

import lombok.Data;

@Data
public class AppUser { // 改为 AppUser 等名称
    private int id;
    private String user_name;
    private String avatar_url;
    private String mobile;

    // Getters 和 Setters
}
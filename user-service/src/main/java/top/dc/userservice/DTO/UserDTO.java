package top.dc.userservice.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String userName;
    private String avatarUrl;
    private String mobile;
}

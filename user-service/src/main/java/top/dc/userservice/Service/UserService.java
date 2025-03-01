package top.dc.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dc.userservice.DTO.UserDTO;
import top.dc.userservice.entity.user;

public interface UserService extends IService<user> {
    UserDTO getUserById(Integer id);
}
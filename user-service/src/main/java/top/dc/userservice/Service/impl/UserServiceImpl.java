package top.dc.userservice.Service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.dc.userservice.DTO.UserDTO;
import top.dc.userservice.Mapper.UserMapper;
import top.dc.userservice.Service.UserService;
import top.dc.userservice.entity.user;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, user> implements UserService {

    @Override
    public UserDTO getUserById(Integer id) {
        // 从数据库获取用户
        user user = this.getById(id);

        // 检查用户是否存在
        if (user == null) {
            return null; // 如果用户不存在，返回 null
        }

        // 创建并返回 UserDTO
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setMobile(user.getMobile());
        return dto;
    }
}

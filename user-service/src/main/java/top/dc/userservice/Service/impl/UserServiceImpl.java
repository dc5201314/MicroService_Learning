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
        user user = this.getById(id);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setMobile(user.getMobile());
        return dto;
    }
}

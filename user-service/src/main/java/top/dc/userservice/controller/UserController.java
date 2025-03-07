package top.dc.userservice.controller;

import top.dc.userservice.config.DcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dc.userservice.DTO.UserDTO;
import top.dc.userservice.Service.UserService;
import top.dc.userservice.entity.ApiResponse;


@RefreshScope
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DcProperties dcProperties; // 注入DcProperties

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable Integer id) {

        if (dcProperties.isServiceFlag()) {
            System.out.println("服务被调用");
            UserDTO user = userService.getUserById(id); // 获取用户信息

            if (user != null) {
                return new ApiResponse<>(200, "成功", user);
            } else {
                return new ApiResponse<>(404, "用户不存在", null);
            }
        } else {
            return new ApiResponse<>(503, "用户服务正在维护中，请稍后。。。", null);
        }
    }
}

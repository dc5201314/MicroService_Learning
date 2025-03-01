package top.dc.contentservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import top.dc.contentservice.VO.UserVO;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @GetMapping("/user/{id}")
    UserVO getUserById(@PathVariable("id") Integer id);
}

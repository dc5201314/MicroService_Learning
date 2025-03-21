package top.dc.contentservice.openfeign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.dc.contentservice.VO.UserVO;

import java.util.ArrayList;

@Component
@Slf4j
public class UserServiceFallBackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable cause) {
        return new UserFeignClient() {
            @Override
            public UserVO getUserById(Integer id) {
                log.error("Fallback for getUserById, cause: {}", cause.getMessage());
                UserVO user = new UserVO();
                user.setId(-1);
                user.setUserName("未知用户");
                user.setAvatarUrl("https://yyf-blog.oss-cn-beijing.aliyuncs.com/yyf-blog/avatar/default.jpg");
                return user;
            }

        };
    }
}

package top.dc.userservice.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.dc.userservice.entity.user;

@Mapper
public interface UserMapper extends BaseMapper<user> {
    // 可以添加自定义查询方法
}
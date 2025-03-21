package top.dc.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.imagerecog")
public class AliyunImageRecogConfig {
    private String endpoint;
}

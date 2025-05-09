package top.dc.userservice.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    private String host;
    private int port;
    private String password;
    private int database;
}

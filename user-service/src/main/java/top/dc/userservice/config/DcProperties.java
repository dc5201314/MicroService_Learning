package top.dc.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dc")
@Data
public class DcProperties {

    private boolean serviceFlag;
}

package top.dc.contentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import top.dc.contentservice.handler.SentinelConfig;

@SpringBootApplication
@EnableFeignClients(basePackages = "top.dc.contentservice.openfeign")
@Import({SentinelConfig.class})

public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }

}

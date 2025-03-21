package top.dc.contentservice.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.context.annotation.Bean;

public class SentinelConfig {
    @Bean
    public BlockExceptionHandler sentinelBlockExceptionHandler() {
        return new SentinelExceptionHandler();
    }
    @Bean
    public RequestOriginParser requestOriginParser() {
       return  new SentinelRequestOriginParser();
    }
}

package top.dc.userservice.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssClientConfig {

    @Autowired
    private OssConfig ossConfig;

    public OssClientConfig(OssConfig ossConfig) {
        System.out.println("OSS AccessKeyId: " + ossConfig.getAccessKeyId());
        System.out.println("OSS BucketName: " + ossConfig.getBucketName());
    }
    @Bean
    public OSS createOssClient() {
        return new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
        );
    }
}

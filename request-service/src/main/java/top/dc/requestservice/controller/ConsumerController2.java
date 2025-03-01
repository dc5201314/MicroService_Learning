package top.dc.requestservice.controller;

import jakarta.annotation.Resource;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class ConsumerController2 {

    @Resource
    private RestTemplate restTemplate;

    private final String SERVICE_URL = "http://localhost:8080";

    @GetMapping("/restTemplateTest")
    public String restTemplateTest()
    {
        return restTemplate.getForObject(SERVICE_URL + "/hello", String.class);
    }
}

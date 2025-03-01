package top.dc.requestservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ConsumerController5 {
    private final String SERVICE_URL = "https://www.wanandroid.com";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(SERVICE_URL)
            .build();

    @GetMapping("/webClientTest2")
    public Mono<String> webClientTest() {
        // 使用 WebClient 发起 GET 请求，获取返回响应并直接返回
        return webClient
                .get()
                .uri("/blog/show/2") // URL 路径
                .retrieve()
                .bodyToMono(String.class); // 返回的是 Mono<String>，直接在响应上返回
    }
}

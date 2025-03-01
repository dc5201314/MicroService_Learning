package top.dc.requestservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ConsumerController4 {
    private final String SERVICE_URL = "https://www.wanandroid.com";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(SERVICE_URL)
            .build();

    @GetMapping("/webClientTest1")
    public Mono<String> webClientTest(@RequestParam(value = "id", defaultValue = "2") int id) {
        // 使用 WebClient 发起 GET 请求，带上查询参数
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/blog/show/{id}") // URL 路径
                        .build(id)) // 带上路径参数
                .retrieve()
                .bodyToMono(String.class); // 返回的是 Mono<String>
    }
}
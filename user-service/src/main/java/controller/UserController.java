package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    private final String aiServiceUrl = "http://localhost:8085/ai";
    @GetMapping("/user1")
    public String getUser(@RequestParam String username){
        return "Hello " + username;
    }
    @GetMapping("/ask") // 新增问答接口
    public String ask(@RequestParam String question) {
        try {
            // 调用 AI 服务的接口
            String aiResponse = restTemplate.getForObject(aiServiceUrl + "?content=" + question, String.class);
            return "AI says: " + aiResponse;
        } catch (Exception e) {
            // 处理异常并返回错误信息
            return "Error occurred: " + e.getMessage();
        }
    }
}

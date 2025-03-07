package top.dc.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.dc.userservice.Service.DeepSeekService;

@RestController
@RequestMapping("/api/deepseek")
@AllArgsConstructor
public class DeepSeekController {

    private final DeepSeekService deepSeekService;


    @PostMapping("/chat")
    public String chatWithAI(@RequestBody String userInput) {
        // 将用户输入发送到 DeepSeek，并获取响应
        return deepSeekService.chat(userInput);
    }
}

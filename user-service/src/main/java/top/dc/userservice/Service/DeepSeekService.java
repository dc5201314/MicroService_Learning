package top.dc.userservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.dc.userservice.config.DeepSeekConfig;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class DeepSeekService {

    private final DeepSeekConfig deepSeekConfig;
    private final ObjectMapper objectMapper;



    public String chat(String userInput) {
        // 发送聊天请求到 DeepSeek API
        String url = "https://api.deepseek.com/chat/completions";

        // 创建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");
        requestBody.put("frequency_penalty", 0);
        requestBody.put("max_tokens", 2048);
        requestBody.put("presence_penalty", 0);
        requestBody.put("stop", null);
        requestBody.put("stream", false);
        requestBody.put("temperature", 1);
        requestBody.put("top_p", 1);
        requestBody.put("logprobs", false);
        requestBody.put("top_logprobs", null);

        // 定义 messages
        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", "You are a helpful assistant"),
                Map.of("role", "user", "content", userInput)
        );
        requestBody.put("messages", messages);

        // 将请求体序列化为 JSON
        String body;
        try {
            body = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing request body", e);
        }

        HttpResponse<String> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + deepSeekConfig.getApiKey())
                .body(body)
                .asString();

        return response.getBody();
    }
}

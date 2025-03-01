package top.dc.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/payment")
    public String getPaymentInfo(@RequestParam String productId) {
        String paymentServiceUrl = "http://127.0.0.1:8090/api/payment"; // Python 服务链接
        String requestJson = "{\"amount\": 6000}"; // 假设收到的支付金额为 6000

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 将 JSON 数据封装到 HttpEntity 中
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        try {
            // 使用 RestTemplate 发送 POST 请求
            String response = restTemplate.postForObject(paymentServiceUrl, requestEntity, String.class);

            // 解析响应
            // 假设 response 是以 JSON 字符串的形式返回
            String paymentStatus = parsePaymentResponse(response);

            return "产品ID: " + productId + "，产品名称: 金朵福，价格: 5200元，" +
                    paymentStatus;
        } catch (Exception e) {
            return "产品ID: " + productId + "，产品名称: 金朵福，价格: 5200元，支付请求失败: " + e.getMessage();
        }
    }

    private String parsePaymentResponse(String response) {
        // 这里可以进行简单的字符串解析，或者使用 JSON 库来解析
        // 下面的解析方式假设您的响应格式是固定的，确保根据实际情况选择解析方法。
        if (response.contains("\"status\":\"success\"")) {
            // 提取所需数据
            double total = 5200.0;
            double received = 6000.0;
            double change = 800.0;
            return "支付成功！支付总金额: ¥" + total + "，您支付的金额: ¥" + received + "，找零: ¥" + change + "。";
        } else {
            return "支付失败，具体原因请查看错误信息。";
        }
    }
}

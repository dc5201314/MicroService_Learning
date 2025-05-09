package top.dc.orderservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/order")
    public String createOrder(@RequestParam String username ,@RequestParam String productId){
        String userServiceUrl = "http://localhost:8081/user?username=" + username;
        String productServiceUrl = "http://localhost:8083/product?productId=" + productId;

        String userInfo = restTemplate.getForObject(userServiceUrl, String.class);
        String productInfo = restTemplate.getForObject(productServiceUrl, String.class);

        return "订单创建成功，用户信息：" + userInfo + "，产品信息：" + productInfo;
    }
}

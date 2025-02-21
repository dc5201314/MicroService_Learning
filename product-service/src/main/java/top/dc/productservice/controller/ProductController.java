package top.dc.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/product")
    public String getProductInfo(@RequestParam String productId) {

        return "产品ID: " + productId + ", 产品名称: 金朵福，价格5200元";
    }
}
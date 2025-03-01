package top.dc.helloservice.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class HelloService {
    public String getName(){
        return "hello";
    }
    @GetMapping("/sayhello")
    public String seyhello(String name) {
        return "hello" + name;
    }
}
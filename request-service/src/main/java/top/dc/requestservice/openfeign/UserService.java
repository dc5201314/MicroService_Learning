package top.dc.requestservice.openfeign;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.dc.requestservice.entity.AppUser;

@FeignClient(value = "user-service", url = "http://localhost:8090")
public interface UserService {

    @GetMapping(value = "/user/{id}")
    AppUser getUserById(@PathVariable("id") int id);

    @PostMapping(value = "/user")
    void createUser(@RequestBody AppUser user);

    @PutMapping(value = "/user/{id}")
    void updateUser(@PathVariable("id") int id, @RequestBody AppUser user);

    @DeleteMapping(value = "/user/{id}")
    void deleteUser(@PathVariable("id") int id);
}
package fr.etrangecantina.services.msfrontend.client;

import fr.etrangecantina.services.msfrontend.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-users", url = "http://localhost:50020/users/v1/user")
public interface UserClient {

    @GetMapping("/list")
    List<User> getAllUsers();

    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") String id);

    @PostMapping
    User createUser(@RequestBody User user);

    @PostMapping("/edit")
    User editUser(@RequestParam("id") String id, @RequestBody User user);
}

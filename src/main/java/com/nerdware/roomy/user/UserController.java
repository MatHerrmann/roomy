package com.nerdware.roomy.user;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping(path = "{userID}")
    public void deleteUser(@NotNull @PathVariable("userID") long id){
        userService.deleteUser(id);
    }

    @PutMapping(path = "{userID}")
    public void updateStudent(@NotNull @PathVariable("userID") long userID,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email){
        userService.updateUser(userID, name, email);
    }

}

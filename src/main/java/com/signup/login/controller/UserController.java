package com.signup.login.controller;

import com.signup.login.entity.User;
import com.signup.login.exception.RecordNotFoundException;
import com.signup.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping({"create", "signUp"})
public class UserController {
    @Autowired
    private UserService userService;

  //  Getting only by Admin
//    @GetMapping("/fetchByAdmin")
//    public List<User> getAllUser() throws RecordNotFoundException {
//        return userService.getAllUsers();
//    }
    //simple post mapping
    @PostMapping({"createUserSimply", "signUpSimply"})
    public User addUser(@RequestBody User user) throws RecordNotFoundException {
        return userService.saveUser(user);
    }

    //post mapping for password encryption
    @PostMapping({"createUser", "signUp"})
    public String create(@RequestBody User user) throws Exception {
        return userService.create(user);
    }

 //deActive User
    @DeleteMapping("/deActivate/{id}")
    public User DeActiveUser(@PathVariable int id){
        return userService.DeActiveUser(id);
    }

    //Activate user
    @DeleteMapping("/Activate/{id}")
    public User ActivateUser(@PathVariable int id){
        return userService.ActivateUser(id);
    }

    //login form through params
    @PostMapping("/login")
    public String Login(
            @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) throws RecordNotFoundException {
        return userService.Login(email, password);
    }

    // get mapping for admin
    @GetMapping("login/admin")
    public List<User> fetchAllByAdmin(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password)throws RecordNotFoundException{
        return userService.fetchAll(email, password);
    }


    // Login and update only Moderator
    @PutMapping("login/moderator")
    public User fetchOnlyModerator(@RequestParam (name = "email") String email, @RequestParam(name = "password") String password, @RequestBody User user) throws RecordNotFoundException{
        return  userService.fetchOnlyModerator(email, password, user);
    }

    //Login and update by User only for user
    @PutMapping("login/user")
    public User updateUser(@RequestParam (name = "email") String email, @RequestParam(name = "password") String password, @RequestBody User user) throws RecordNotFoundException {
        return userService.LoginAndUpdateUser(email, password, user);
    }
    @PutMapping("login/admin")
    public List<User> updateAdmin(@RequestParam (name = "email") String email, @RequestParam(name = "password") String password, @RequestBody User user) throws RecordNotFoundException {
        return userService.LoginAndUpdateAllByAdmin(email, password, user);
    }


}

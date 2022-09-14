package com.signup.login.controller;

import com.signup.login.entity.Role;
import com.signup.login.entity.User;
import com.signup.login.exception.RecordNotFoundException;
import com.signup.login.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    //Getting list of users
    @GetMapping("/fetchAllUser")
    public List<User> getAllUser(){
        return roleService.getUsers();
    }
    //post roles
    @PostMapping("/addRole")
    public Role addRole(@RequestBody Role role) throws RecordNotFoundException {
        return roleService.AddRole(role);

    }


}

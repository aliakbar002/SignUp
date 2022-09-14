package com.signup.login.service;

import com.signup.login.entity.Role;
import com.signup.login.entity.User;
import com.signup.login.exception.RecordNotFoundException;
import com.signup.login.repository.RoleRepository;
import com.signup.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    User user = new User();
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public Role AddRole(Role role) throws RecordNotFoundException {
        if  (userRepository.findByEmail(user.getEmail()) != null){
            throw new RecordNotFoundException("Email already exist");
        }
        else {
        return roleRepository.save(role);
        }
    }


}

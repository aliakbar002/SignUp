package com.signup.login.repository;

import com.signup.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail( String email);
    User findById(int id);

  //  User findAll(User user);

    //   User registerUser(User user);

    // boolean findByEmail(String email);
    //User findByEmailAndPassword(User email, User password);

   // boolean findByEmailAndPassword(String email, String password);


    //   User findByModerator(String name);

//   User findByModerator(String name);

}

package com.signup.login.repository;

import com.signup.login.entity.Role;
import com.signup.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  //  Role findByNameIsStartingWithModerator(String name);
    Role findNameById(int id);

    Role findById(int id);
  List<Role> findByNameIs(String name);
    //Role fundNameByEmail(User email);
 // Role findRoleByName(String name);
  //  Role findByName(String name);
   // Role findNameById(String name);
   // Role findByEmail(String email);

  //  Role findNameByEmail(String email);
}

package com.signup.login.service;

import com.signup.login.Dto.UserDto;
import com.signup.login.entity.ConstantsVal;
import com.signup.login.entity.Role;
import com.signup.login.entity.User;
import com.signup.login.entity.UserHelpers;
import com.signup.login.exception.RecordNotFoundException;
import com.signup.login.repository.RoleRepository;
import com.signup.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    // Get All Mapping
    public List<User> getAllUsers() throws RecordNotFoundException {
        Role role = new Role();
        ConstantsVal constantsVal = new ConstantsVal();
        if (Objects.equals(role.getName(), constantsVal.getAdmin())) {
            throw new RecordNotFoundException("Admin not found");
        } else {
            return new ArrayList<>(userRepository.findAll());

        }
    }


//    //post mapping saving user
//    public User saveUser(User user) throws RecordNotFoundException {
//        int id= user.getRole().getId();
//        Role role = roleRepository.findById(id);
//        if (role!=null){
//            user.setRole(role);
//            }
//        else {
//            throw new RecordNotFoundException("role not found");
//        }
//        int uid=user.getCreatedBy().getId();
//       user=userRepository.findById(uid);
//       if (user!=null) {
//           user.setCreatedBy(user);
//       }
//        assert user != null;
//        if (userRepository.findByEmail(user.getEmail()) != null){
//            throw new RecordNotFoundException("Email already exist");
//        }
//        else {
//            return userRepository.save(user);
//
//       }
//
//    }

   //  post mapping password encryption
    public String create(@RequestBody User user) throws Exception {

        int id= user.getRole().getId();
        Role role = roleRepository.findById(id);
        if (role!=null){
            user.setRole(role);
        }else {
            throw  new RecordNotFoundException("Role should not be regenerated");
        }
       int uid=user.getCreatedBy().getId();
         user=userRepository.findById(uid);
        if (user!=null){
        user.setCreatedBy(user);

        }else {
            throw  new RecordNotFoundException(" user is null");
        }
       if (userRepository.findByEmail(user.getEmail()) != null)
           throw new RecordNotFoundException("Email already exist");

        String response = "";
       try {
            String base64Password = UserHelpers.encryptStringToBase64(user.getPassword());
            user.setPassword(base64Password);

                userRepository.save(user);
                response = "User saved successfully.";

        } catch (Exception e) {
            throw new Exception("User not saved." + e.getMessage());
        }

        return response;

    }

    public String decrypt(User user) throws Exception {
       String response = "";

        try {
           User user1 = userRepository.findByEmail(user.getEmail());
            if (Objects.equals(user1.getPassword(), UserHelpers.decryptStringToBase64(user.getPassword()))) {
                user1=user;
                response = "Login successful";
            }
        } catch (Exception e) {
            throw new Exception("Login Unsuccessful." + e.getMessage());
        }
        return response ;

    }



    //DeActive User By Delete mapping
    public User DeActiveUser(int id) {
        User deActiveUser = userRepository.findById(id);
        UserDto userDto = new UserDto();
        assert deActiveUser != null;
        deActiveUser.setStatus(userDto.getDeActive());
        return userRepository.save(deActiveUser);


    }

    //Activate By delete mapping
    public User ActivateUser(int id) {
        User ActivateUser = userRepository.findById(id);
        UserDto userDto = new UserDto();
        assert ActivateUser != null;
        ActivateUser.setStatus(userDto.getActive());
        return userRepository.save(ActivateUser);
    }

    //update user by admin
    public User updateUser(User user) {
        User updateUser = userRepository.findById(user.getId());
        assert updateUser != null;
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setStatus(user.getStatus());
        updateUser.setCreatedAt(user.getCreatedAt());
        updateUser.setRole(user.getRole());
        return userRepository.save(updateUser);
    }

    //post mapping for login
    public String Login( String email, String password) throws RecordNotFoundException {
        Role role;
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RecordNotFoundException("user is empty");
        } else if (!Objects.equals(email, user.getEmail()) || !Objects.equals(password, user.getPassword())) {
            throw new RecordNotFoundException("put correct email or password");

        } else {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                user.setUpdatedAt(user.getUpdatedAt());
                userRepository.save(user);
                int id=user.getRole().getId();
                role=roleRepository.findNameById(id);
               // role = roleRepository.findNameById(id);
                return "Login Successfully" + " As a: " + role.getName();
            } else {
                throw new RecordNotFoundException("Invalid email or password");
            }
        }
    }

    // Get mapping By Admin to fetch list
    public List<User> fetchAll(String email, String password) throws RecordNotFoundException {
        Role role;
        User user = userRepository.findByEmail(email);
        assert user != null;
        if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
            user.setUpdatedAt(user.getUpdatedAt());
            userRepository.save(user);
            int id=user.getRole().getId();
            role=roleRepository.findNameById(id);
          //  role = roleRepository.findNameById(id);
            String admin = "admin";
            if (Objects.equals(role.getName(), admin)) {
                return userRepository.findAll();
            }
        } else {
            throw new RecordNotFoundException("you dont have permission");
        }
        throw new RecordNotFoundException(" Email and password is not correct");
    }




    //GetMapping By User to get list
//    public List<User> fetchAllByUser(String email, String password) throws RecordNotFoundException {
//
//        User user = userRepository.findByEmail(email);
//        Role role;
//        assert user != null;
//        if (email.equals(user.getEmail()) && password.equals(user.getPassword())){
//            user.setUpdatedAt(user.getUpdatedAt());
//            userRepository.save(user);
//         int id=user.getRole().getId();
//           role=roleRepository.findNameById(id);
//           // role = roleRepository.findNameByEmail(email);
//            String user1="user";
//            if (Objects.equals(role.getName(), user1)){
//                return userRepository.findAll();
//            }
//        }else {
//            throw  new RecordNotFoundException("you dont have permission");
//        }
//        throw new RecordNotFoundException(" User Does not exist");
//    }

    //Login and update only moderator
    public User fetchOnlyModerator(String email, String password, User user) throws RecordNotFoundException {
    User loginUser = userRepository.findByEmail(email);
        Role role;
        assert loginUser != null;
        if (email.equals(loginUser.getEmail()) && password.equals(loginUser.getPassword())){
            loginUser.setUpdatedAt(loginUser.getUpdatedAt());
            int id=loginUser.getRole().getId();
            role=roleRepository.findNameById(id);
            userRepository.save(loginUser);
            String moderator="moderator";
            assert role != null;
            if (moderator.equals(role.getName())){
          User  updateUser = userRepository.findById(user.getId());
                assert updateUser != null;
                updateUser.setEmail(user.getEmail());
                updateUser.setPassword(user.getPassword());
                return  userRepository.save(updateUser);
            }
        }else {
            throw  new RecordNotFoundException("you dont have permission");
        }
        throw new RecordNotFoundException(" User Does not exist");
    }

    //Login and update only User
    public User LoginAndUpdateUser(String email, String password, User user) throws RecordNotFoundException {
        User userLogin = userRepository.findByEmail(email);
        Role role;
        assert user != null;
        if (Objects.equals(email, userLogin.getEmail()) && Objects.equals(password, userLogin.getPassword())){
            assert userLogin != null;
            userLogin.setUpdatedAt(userLogin.getUpdatedAt());
            userRepository.save(userLogin);
            int id=userLogin.getRole().getId();
            role=roleRepository.findNameById(id);
            String user1="user";
            if (Objects.equals(role.getName(), user1)){
                User  updateUser = userRepository.findById(user.getId());
                assert updateUser != null;
                updateUser.setEmail(user.getEmail());
                updateUser.setPassword(user.getPassword());
                return  userRepository.save(updateUser);
            }
        }else {
            throw  new RecordNotFoundException("you dont have permission");
        }
        throw new RecordNotFoundException(" Enter correct email and password");
    }

    //Login and update by admin
    public List<User> LoginAndUpdateAllByAdmin(String email, String password, User user) throws RecordNotFoundException {

        User userLogin = userRepository.findByEmail(email);
        Role role;
        assert userLogin != null;
        if (email.equals(userLogin.getEmail()) && password.equals(userLogin.getPassword())){
            userLogin.setUpdatedAt(userLogin.getUpdatedAt());
            userRepository.save(userLogin);
            int id=userLogin.getRole().getId();
            role=roleRepository.findNameById(id);
            String admin="admin";
            if (Objects.equals(role.getName(), admin)){
                User  updateUser = userRepository.findById(user.getId());
                assert updateUser != null;
                updateUser.setEmail(user.getEmail());
                updateUser.setPassword(user.getPassword());

                 userRepository.save(updateUser);
             return    userRepository.findAll();
            }


    }else {
        throw  new RecordNotFoundException("you dont have permission");
    }
        throw  new RecordNotFoundException("you dont have permission");
}
}




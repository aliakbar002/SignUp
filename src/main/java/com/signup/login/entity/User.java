package com.signup.login.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private int status;

   @CreationTimestamp
   @Column(name = "created_at")
   private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
    private Date updatedAt;



    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)//self relation with user
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonIgnore
   @OneToMany(mappedBy = "createdBy")
   private List<User> users;

//    @OneToOne(cascade = CascadeType.ALL)//relation with role
//    @JoinColumn(name = "roleid",referencedColumnName = "id")
//    private Role role;

    //ManyToOne with
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
   @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;



}

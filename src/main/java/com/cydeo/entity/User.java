package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(nullable = false,updatable = false)  // we don't want userName to be null or updated, we find the user by unique username
    private String userName;

    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne // many users can have only one role
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

//    public User(Long id, LocalDateTime insertDateTime, Long insertUserId, LocalDateTime lastUpdateDateTime, Long lastUpdateUserId, String firstName, String lastName, String userName, String passWord, boolean enabled, String phone, Role role, Gender gender) {
//        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.userName = userName;
//        this.passWord = passWord;
//        this.enabled = enabled;
//        this.phone = phone;
//        this.role = role;
//        this.gender = gender;
//    }

}

package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
@Where(clause="is_deleted=false")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

   // @Column(nullable = false,updatable = false)  // we don't want userName to be null or updated, we find the user by unique username
    private String userName;

    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne // many users can have only one role
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;



}

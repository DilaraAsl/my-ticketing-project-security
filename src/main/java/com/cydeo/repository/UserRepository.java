package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username); // derived query

    @Transactional
    // when we have multiple steps before committing data to a table
    //transactional completes the process when all steps are successful
    // if one step fails, everything rolls back to the original data
    void deleteByUserName(String username);

    List<User> findByRoleDescriptionIgnoreCase(String role);
}

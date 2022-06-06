package com.gm.userservice.userservice.repository;

import com.gm.userservice.userservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
    boolean existsByEmail(String email);

    User findByEmail(String email);
}

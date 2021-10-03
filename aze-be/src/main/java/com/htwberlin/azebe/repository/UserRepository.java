package com.htwberlin.azebe.repository;

import com.htwberlin.azebe.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "DELETE FROM user WHERE name=?1", nativeQuery = true)
    void deleteUserByName(String name);

    @Query(value = "SELECT * FROM user WHERE name=?1", nativeQuery = true)
    User getUserByName(String name);

    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User findByUsername(String name);
}

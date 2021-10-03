package com.htwberlin.azebe.repository;

import com.htwberlin.azebe.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Delete user by name.
     *
     * @param name the name
     */
    @Query(value = "DELETE FROM user WHERE name=?1", nativeQuery = true)
    void deleteUserByName(String name);

    /**
     * Gets user by name.
     *
     * @param name the name
     * @return the user by name
     */
    @Query(value = "SELECT * FROM user WHERE name=?1", nativeQuery = true)
    User getUserByName(String name);

    /**
     * Find by username user.
     *
     * @param name the name
     * @return the user
     */
    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User findByUsername(String name);
}

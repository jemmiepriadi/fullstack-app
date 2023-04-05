package com.project.projectapi.repos;

import com.project.projectapi.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "select * from User where id = :id", nativeQuery = true)
    User findWithId(@Param("id")long id);
}

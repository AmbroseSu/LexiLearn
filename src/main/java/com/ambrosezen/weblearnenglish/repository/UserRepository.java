package com.ambrosezen.weblearnenglish.repository;

import com.ambrosezen.weblearnenglish.entities.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

  Optional<Users> findByEmail(String email);
  @Query("SELECT us FROM Users us WHERE us.userName LIKE :username")
  Optional<Users> findByUsername(String username);

  @Query("SELECT us FROM Users us WHERE us.email LIKE :email")
  Users findUserByEmail(String email);

  @Query("SELECT us FROM Users us WHERE us.userName LIKE :username")
  Users findUserByUsername(String username);

}

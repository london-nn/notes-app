package com.group.notes_app.repository;

import com.group.notes_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUsername(String username);

    public Optional<User> findOptionalUserByUsername(String username);

    public boolean existsUserByUsername(String username);
}

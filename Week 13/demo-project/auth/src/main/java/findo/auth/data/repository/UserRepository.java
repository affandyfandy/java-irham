package findo.auth.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.auth.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

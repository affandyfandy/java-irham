package findo.auth.service;

import java.util.Optional;

import findo.auth.data.entity.User;
import findo.auth.dto.AuthDTO;

public interface AuthenticationService {
    User saveUser(User user);
    User findByUsername(String username);
    Optional<String> login(AuthDTO authRequest);
}

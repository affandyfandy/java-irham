package findo.auth.service;

import findo.auth.data.entity.User;

public interface AuthenticationService {
    User saveUser(User user);
    User findByUsername(String username);
}

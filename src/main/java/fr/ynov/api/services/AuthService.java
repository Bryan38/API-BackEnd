package fr.ynov.api.services;

import fr.ynov.api.dto.UserDto;
import fr.ynov.api.entities.User;

import javax.servlet.http.HttpSession;

public interface AuthService {

    // Authentification
    boolean isAccountExist(User user);
    boolean isCredentialsUserAreCorrect(User user);
    UserDto getUserInfoByEmail(String email);
    boolean isAuthenticated(HttpSession session);

}

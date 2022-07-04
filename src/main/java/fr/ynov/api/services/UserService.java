package fr.ynov.api.services;

import fr.ynov.api.entities.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface UserService {

    boolean isEmailAccountExist(User user);
    List<User> getAllUsers();
    User createUser(User user) throws ResponseStatusException;
    User updateUser(User user, Long idUser) throws ResponseStatusException;
    User getUserById(Long idUser) throws ResponseStatusException;
    boolean deleteUserById(Long idUser) throws ResponseStatusException;

}

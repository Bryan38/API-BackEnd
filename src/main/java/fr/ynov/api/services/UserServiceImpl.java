package fr.ynov.api.services;

import fr.ynov.api.entities.User;
import fr.ynov.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static fr.ynov.api.utils.Constantes.ErrorMessage.EXCEPTION_USER_DOESNT_EXISTS;
import static fr.ynov.api.utils.Constantes.ErrorMessage.EXCEPTION_USER_EMAIL_ALREADY_EXISTS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public boolean isEmailAccountExist(User user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    @Override
    public User createUser(User user) throws ResponseStatusException {
        if(!isEmailAccountExist(user)) {
            //On encrypte le mot de passe
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, EXCEPTION_USER_EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public User updateUser(User user, Long idUser) throws ResponseStatusException{
        Optional<User> userOpt = userRepository.findById(idUser);
        if(userOpt.isPresent()) {
            User userTemp = userOpt.get();

            userTemp.setId(idUser);

            if (user.getFirstname() != null){
                userTemp.setFirstname(user.getFirstname());
            }

            if (user.getLastname() != null){
                userTemp.setLastname(user.getLastname());
            }

            if (user.getEmail() != null){
                userTemp.setEmail(user.getEmail());
            }

            if (user.getPhone() != null){
                userTemp.setPhone(user.getPhone());
            }

            if (user.getPassword() != null){
                //On encrypte le mot de passe
                userTemp.setPassword(passwordEncoder.encode(userTemp.getPassword()));
            }
            return userRepository.save(userTemp);

        } else {
            //L'utilisateur n'existe pas -> throw exception
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXCEPTION_USER_DOESNT_EXISTS);
        }
    }

    @Override
    public User getUserById(Long idUser) throws ResponseStatusException {
        Optional<User> userOpt = userRepository.findById(idUser);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            return user;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXCEPTION_USER_DOESNT_EXISTS);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public boolean deleteUserById(Long idUser) throws ResponseStatusException{
        Optional<User> userOpt = userRepository.findById(idUser);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            userRepository.delete(user);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXCEPTION_USER_DOESNT_EXISTS);
        }
    }
}

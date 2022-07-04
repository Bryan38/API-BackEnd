package fr.ynov.api.services;

import fr.ynov.api.dto.UserDto;
import fr.ynov.api.entities.User;
import fr.ynov.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapService modelMapService;


    @Override
    public boolean isAccountExist(User user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }


    @Override
    public boolean isCredentialsUserAreCorrect(User user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
        if(userOpt.isPresent()) {
            return passwordEncoder.matches(user.getPassword(), userOpt.get().getPassword());
        } else {
            //Compte n'existe pas
            return false;
        }
    }

    @Override
    public UserDto getUserInfoByEmail(String email) {
        if(email.isEmpty()) {
            return null;
        }
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(user -> modelMapService.convertToDto(user)).orElse(null);
    }

    @Override
    public boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("user") != null;
    }


}

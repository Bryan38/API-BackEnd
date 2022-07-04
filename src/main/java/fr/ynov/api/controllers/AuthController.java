package fr.ynov.api.controllers;

import fr.ynov.api.dto.UserDto;
import fr.ynov.api.entities.User;
import fr.ynov.api.services.AuthService;
import fr.ynov.api.services.ModelMapService;
import fr.ynov.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class AuthController {

    @Autowired
    private ModelMapService modelMapService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Connexion Utilisateur")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public boolean userConnection(HttpServletRequest request, @RequestBody UserDto userDto) {
        User userDao = modelMapService.convertToDao(userDto);
        if(authService.isCredentialsUserAreCorrect(userDao)) {
            request.getSession().setAttribute("user", authService.getUserInfoByEmail(userDto.getEmail()));
            return true;
        }
        else {
            return false;
        }
    }

    @Operation(summary = "Déconnexion Utilisateur")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean userDisconnection(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(request.isRequestedSessionIdValid() && session != null) {
            session.invalidate();
            return true;
        }
        else {
            return false;
        }
    }

    @Operation(summary = "Création nouvel utilisateur")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public User createUserRegister(@RequestBody UserDto userDto){
        try {
            User userCreated = userService
                    .createUser(modelMapService.convertToDao(userDto));
            return userCreated;
        }
        catch (EntityExistsException e){
            User user = new User();
            return user;
        }
    }

}

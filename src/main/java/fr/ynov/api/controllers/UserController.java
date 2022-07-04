package fr.ynov.api.controllers;

import fr.ynov.api.dto.UserDto;
import fr.ynov.api.entities.User;
import fr.ynov.api.exception.MyResourceNotFoundException;
import fr.ynov.api.services.ModelMapService;
import fr.ynov.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.util.List;

import static fr.ynov.api.utils.Constantes.ErrorMessage.*;
import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ModelMapService modelMapService;

    @Autowired
    private UserService userService;


    @Operation(summary = "Récupérer tous les utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) })
    })
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @Operation(summary = "Récupérer un utilisateur par son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @RequestMapping(path = "/{idUser}", method = RequestMethod.GET)
    public User getUser(@PathVariable(name = "idUser") Long idUser){
        try {
            if (null==idUser || idUser.equals(0L) || !isNumeric(idUser)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EXCEPTION_ID_NOT_VALID);
            }
        return userService.getUserById(idUser);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXCEPTION_USER_DOESNT_EXISTS, exc);
        }
    }


    @Operation(summary = "Créer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User create",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "409", description = "User email already exists",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody UserDto userDto){
        try {
            User userCreated = userService.createUser(modelMapService.convertToDao(userDto));
            return userCreated;
        }
        catch (EntityExistsException e){
            throw new EntityExistsException(e);
        }
    }


    @Operation(summary = "Mettre à jour un utilisateur avec son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @RequestMapping(path = "/{idUser}", method = RequestMethod.PATCH)
    public User updateUser(@RequestBody UserDto userDto, @PathVariable(value = "idUser") Long idUser){
        try {
            if (null == idUser || idUser.equals(0L) || !isNumeric(idUser)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EXCEPTION_ID_NOT_VALID);
            }
            return userService.updateUser(modelMapService.convertToDao(userDto), idUser);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXCEPTION_USER_DOESNT_EXISTS, exc);
        }
    }


    @Operation(summary = "Supprimer un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User delete",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @RequestMapping(path = "/{idUser}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable(value = "idUser") Long idUser) {
        try {
            if (null == idUser || idUser.equals(0L) || !isNumeric(idUser)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EXCEPTION_ID_NOT_VALID);
            }
            return userService.deleteUserById(idUser);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXCEPTION_USER_DOESNT_EXISTS, exc);
        }
    }
}

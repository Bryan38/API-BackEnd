package fr.ynov.api.services;

import fr.ynov.api.dto.*;
import fr.ynov.api.entities.*;

public interface ModelMapService {
    UserDto convertToDto(User user);
    User convertToDao(UserDto userDto);

}

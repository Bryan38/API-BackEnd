package fr.ynov.api.services;

import fr.ynov.api.dto.*;
import fr.ynov.api.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapServiceImpl implements ModelMapService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User convertToDao(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}

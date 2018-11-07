package pl.edu.pwsztar.shapewars.services.interfaces;

import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.UserDto;
import pl.edu.pwsztar.shapewars.exceptions.EmailExistsException;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;
}

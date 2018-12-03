package api.interfaces;

import api.exceptions.NonUniqueUsernameException;
import user.model.User;

import java.io.IOException;

public interface IUserService {
    User login(String name, String password) throws IOException;
    void register(User user) throws IOException, NonUniqueUsernameException;
}

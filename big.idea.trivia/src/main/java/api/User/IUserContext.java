package api.User;

import api.exceptions.IncorrectCredentialsException;
import user.model.User;

import java.util.ArrayList;

public interface IUserContext {
    ArrayList<User> getAll();
    boolean create(User user);
    User getByCredentials(String name, String password) throws IncorrectCredentialsException;
}

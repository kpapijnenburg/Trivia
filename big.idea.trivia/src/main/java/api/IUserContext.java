package api;

import user.model.User;

import java.util.ArrayList;

public interface IUserContext {
    ArrayList<User> getAll();
    boolean create(User user);
}

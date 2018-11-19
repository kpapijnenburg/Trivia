package api;


import api.exceptions.IncorrectCredentialsException;
import api.exceptions.NonUniqueUsernameException;
import user.model.User;

import java.util.ArrayList;

public class UserRepository {

    private IUserContext context;

    public UserRepository(IUserContext context) {
        this.context = context;
    }

    public User login(String name, String password) throws IncorrectCredentialsException {
        ArrayList<User> users = getAll();
        User userToReturn = null;

        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                userToReturn = user;
            }
        }

        if (userToReturn == null){
            throw new IncorrectCredentialsException("Username or password is incorrect.");
        }

        return userToReturn;
    }


    public void register(User user) throws NonUniqueUsernameException {
        if (!context.create(user)){
            throw new NonUniqueUsernameException("Username is already taken.");
        }
    }

    private ArrayList<User> getAll() {
        return context.getAll();
    }


}

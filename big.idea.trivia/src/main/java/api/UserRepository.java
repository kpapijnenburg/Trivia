package main.java.api;


import main.java.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private IUserContext context;

    public UserRepository(IUserContext context) {
        this.context = context;
    }

    public User login(String name, String password) throws Exception {
        ArrayList<User> users = getAll();
        User userToReturn = null;

        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                userToReturn = user;
            }
        }

        if (userToReturn == null){
            throw new Exception();
        } else {
            return userToReturn;
        }
    }


    public void register(String name, String password) {
        //todo context toevoegen
    }

    public ArrayList<User> getAll() {
        return context.getAll();
    }
}

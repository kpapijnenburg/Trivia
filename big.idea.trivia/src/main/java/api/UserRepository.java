package api;


import main.java.api.IUserContext;
import main.java.user.model.User;

import java.util.ArrayList;

class UserRepository {

    private main.java.api.IUserContext context;

    UserRepository(IUserContext context) {
        this.context = context;
    }

    User login(String name, String password) throws Exception {
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


    void register(String name, String password) {
        //todo context toevoegen
    }

    private ArrayList<User> getAll() {
        return context.getAll();
    }
}

package main.java.api;


import main.java.user.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserSqlContext implements IUserContext {

    //todo database opzetten en sql schrijven.
    private ArrayList<User> users;


    public UserSqlContext() {
        users = new ArrayList<User>();

        users.add(new User(0, "jan", "test"));
        users.add(new User(1, "admin", "admin"));
        users.add(new User(2, "koen", "test"));
    }

    @Override
    public ArrayList<User> getAll() {
        return this.users;
    }
}

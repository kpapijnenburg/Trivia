package main.java.api;

import main.java.user.model.User;

import java.util.ArrayList;

public interface IUserContext {
    ArrayList<User> getAll();
}

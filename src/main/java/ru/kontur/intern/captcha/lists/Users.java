package ru.kontur.intern.captcha.lists;

import ru.kontur.intern.captcha.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private static volatile List<User> users;

    public static List<User> getUsers(){
        if(users == null){
            synchronized (Users.class) {
                if(users == null){
                    users = new ArrayList<User>();
                }
            }
        }
        return users;
    }

    public static void add(User user){
        synchronized (Users.class) {
            getUsers().add(user);
        }
    }

    public static User getUserByPublicKey(String publicKey) {
        for (User user : getUsers()) {
            if(user.getPublicKey().equals(publicKey)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserBySecretKey(String secretKey) {
        for (User user : getUsers()) {
            if(user.getSecretKey().equals(secretKey)) {
                return user;
            }
        }
        return null;
    }
}

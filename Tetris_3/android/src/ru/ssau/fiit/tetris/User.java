package ru.ssau.fiit.tetris;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    public static final String PLAYER_LOGIN = "player";
    public static final String PLAYER_PASSWORD = "player";

    //private String userId;
    private String username;
    private String password;

    public User() { }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }*/

    //public String getUserId() { return userId; }
    //public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

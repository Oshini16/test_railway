package service;

import model.User;
import structure.UserHashTable;

public class AuthenticationService {
    private final UserHashTable users;

    public AuthenticationService(UserHashTable users) {
        this.users = users;
    }

    public String register(String username, String password) {
        username = username == null ? "" : username.trim();
        if (username.isEmpty() || password == null || password.trim().isEmpty()) {
            return "Username and password cannot be empty.";
        }
        if (users.containsKey(username)) {
            return "Username already exists.";
        }
        users.put(username, new User(username, password));
        return null;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password) ? user : null;
    }
}

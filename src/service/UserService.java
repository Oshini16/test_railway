package service;

import model.User;
import structure.UserHashTable;

public class UserService {
    private final UserHashTable users;

    public UserService(UserHashTable users) {
        this.users = users;
    }

    public User find(String username) {
        return users.get(username);
    }

    public boolean delete(String username) {
        User user = users.get(username);
        if (user == null) {
            return false;
        }
        user.getBookings().forEach(booking -> booking.getTrain().cancelSeat());
        return users.remove(username);
    }
}

package com.revature.services;
import com.revature.daos.UserDAO;
import com.revature.dtos.requests.LoginRequest;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.utils.custom_exceptions.AuthenticationException;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.InvalidRequestException;
import com.revature.utils.custom_exceptions.ResourceConflictException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserService {
    private static UserDAO userDAO = new UserDAO();
    UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public static void validateUsername(String username) throws InvalidRequestException {
        if(!username.matches("^([A-Za-z\\d]{3,15}|[A-Za-z0-9][A-Za-z0-9!#$%&'*+\\-/=?^_`{}|]{0,63}@[A-Za-z0-9.-]{1,253}.[A-Za-z]{2,24})$"))
            throw new InvalidRequestException("Username must start with a letter and consist of between 3 and 15 alphanumeric characters or be a valid email address.");
    }

    public static void validatePassword(String password) throws InvalidRequestException {
        if(!password.matches("^[A-Za-z\\d@$!%*?&]{5,30}$"))
            throw new InvalidRequestException("Password must be between 5 and 30 alphanumeric or special characters.");
    }

    public static User registerUser(NewUserRequest request){
        validateUsername(request.getUsername());
        validatePassword(request.getPassword());
        checkAvailableUsername(request.getUsername());
        userDAO.save(
                new User(
                    UUID.randomUUID(),
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getGiven_name(),
                    request.getSurname(),
                    false,
                    UserRole.valueOf(request.getRole())
                )
        );
        return null;
    }

    public static User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public static void checkAvailableUsername(String username) throws ResourceConflictException {
        if (userDAO.isUsernameAvailable(username)){
            throw new ResourceConflictException("Username is already taken, please choose another.");
        }
    }

    public static User validateLogin(LoginRequest request) throws AuthenticationException {
        User user = userDAO.getUserByUsernameAndPassword(request.getUsername(), request.getPassword());
        if(user == null){
            //TODO tell why unsuccessful.
            throw new AuthenticationException("Login unsuccessful. Please check username and password.");
        }
        return user;
    }

    public static List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public static List<User> getAllActiveUsers() {
        return userDAO.getAllActiveUsers();
    }

    public static List<User> getAllInactiveUsers() {
        return userDAO.getAllInactiveUsers();
    }

    public static void updateUser(User user) {
        userDAO.update(user);
    }

    public static void activateUser(User user) {
        if(user.isActive()){
            throw new BadRequestException("User is already active.");
        } else {
            user.Activate();
            updateUser(user);
        }
    }

    public static void deactivateUser(User user) {
        if(!user.isActive()){
            throw new BadRequestException("User is already inactive.");
        } else {
            user.Deactivate();
            updateUser(user);
        }
    }

    public static void deleteUser(User user) {
        deleteUser(user.getUserID());
    }
    public static void deleteUser(UUID id) {
        userDAO.delete(id);
    }

    public static void changePassword(User user, String password) {
        user.setPassword(password);
        userDAO.update(user);
    }
}

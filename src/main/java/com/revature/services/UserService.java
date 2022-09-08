package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.dtos.requests.LoginRequest;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.dtos.requests.admin.DeleteUserRequest;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.utils.custom_exceptions.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.UUID;

public class UserService {
    private static UserDAO userDAO = new UserDAO();
    UserService(UserDAO userDAO){
        UserService.userDAO = userDAO;
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
        String salt = UUID.randomUUID().toString().replace("-","");
        String hash = hashPassword(request.getPassword().toCharArray(), DatatypeConverter.parseHexBinary(salt));
        userDAO.save(
                new User(
                    UUID.randomUUID(),
                    request.getUsername(),
                    request.getEmail(),
                    salt + ":" + hash,
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

    public static String hashPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            KeySpec ks = new PBEKeySpec(password, salt, 1024, 128);
            return DatatypeConverter.printHexBinary(f.generateSecret(ks).getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static User validateLogin(LoginRequest request) throws AuthenticationException {
        User user = userDAO.getUserByUsername(request.getUsername());
        if (user != null) {
            String[] password = user.getPassword().split(":");
            if (password.length == 2 && password[1].equals(hashPassword(request.getPassword().toCharArray(), DatatypeConverter.parseHexBinary(password[0]))))
                return user;
        }
        throw new AuthenticationException("Login unsuccessful. Please check username and password.");
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

    public static void activateUser(String username) {
        User user = getUserByUsername(username);
        if(user.isActive()){
            throw new BadRequestException("User is already active.");
        } else {
            user.activate();
            updateUser(user);
        }
    }

    public static void deactivateUser(String username) {
        User user = getUserByUsername(username);
        if(!user.isActive()){
            throw new BadRequestException("User is already inactive.");
        } else {
            user.deactivate();
            updateUser(user);
        }
    }
    public static void deleteUser(DeleteUserRequest request){
        deleteUser(getUserByUsername(request.getUsername()));
    }
    public static void deleteUser(User user) {
        deleteUser(user.getUserID());
    }
    public static void deleteUser(UUID id) {
        userDAO.delete(id);
    }

    public static void changePassword(String username, String password) {
        validatePassword(password);
        String salt = UUID.randomUUID().toString().replace("-","");
        String hash = hashPassword(password.toCharArray(), DatatypeConverter.parseHexBinary(salt));
        User user = getUserByUsername(username);
        user.setPassword(salt + ":" + hash);
        userDAO.update(user);
    }

    public static void verifyUserRole(User user, UserRole role){
        if((user.getRole() != role))
            throw new ForbiddenException("Unauthorized User. Active " + role.toString() + " only.");
    }
}

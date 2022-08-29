package com.revature.daos;

import com.revature.models.Principal;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.utils.custom_exceptions.InvalidSQLException;
import com.revature.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO implements DAO<User> {

    @Override
    public void save(User user) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ers_users (user_id, username, email, password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getUserID().toString());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getGivenName());
            ps.setString(6, user.getSurname());
            ps.setBoolean(7, user.isActive());
            ps.setString(8, user.getRole().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(User obj) {throw new InvalidSQLException("Not yet implemented.");}

    @Override
    public void delete(String id) {throw new InvalidSQLException("Not yet implemented.");}

    @Override
    public User getByKey(String key) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ers_users WHERE user_id = ?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        UUID.fromString(rs.getString("user_id")),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        UserRole.valueOf(rs.getString("role_id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(
                        new User(
                                UUID.fromString(rs.getString("user_id")),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("given_name"),
                                rs.getString("surname"),
                                rs.getBoolean("is_active"),
                                UserRole.valueOf(rs.getString("role_id"))
                        )
                );
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return users;
    }

    public Principal getUserByUsernameAndPassword(String username, String password) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Principal(rs.getString("username"), rs.getBoolean("is_admin"));
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return null;
    }
}
package com.revature.daos;

import com.revature.dtos.responses.Principal;
import com.revature.models.User;
import com.revature.utils.custom_exceptions.InvalidSQLException;
import com.revature.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {

    @Override
    public void save(User user) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (username, password, is_admin) VALUES (?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(User obj) {throw new InvalidSQLException("An error occurred when tyring to save to the database.");}

    @Override
    public void delete(String id) {throw new InvalidSQLException("An error occurred when tyring to save to the database.");}

    @Override
    public User getByKey(String key) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"), rs.getBoolean("is_admin"));
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password"), rs.getBoolean("is_admin")));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return users;
    }

    public Principal getUserByUsernameAndPassword(String username, String password) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
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
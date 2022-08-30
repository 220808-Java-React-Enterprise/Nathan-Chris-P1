package com.revature.daos;

import com.revature.models.*;
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
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ers_users (user_id, username, email, password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")){
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
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE ers_users SET username = ?, email = ?, password = ?, given_name = ?, surname = ?, is_active = ?, role_id = ? WHERE user_id = ?")){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getGivenName());
            ps.setString(5, user.getSurname());
            ps.setBoolean(6, user.isActive());
            ps.setString(7, user.getRole().name());
            ps.setString(8, user.getUserID().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to update the database.");
        }
    }

    @Override
    public void delete(String id) {throw new InvalidSQLException("Not yet implemented.");}

    @Override
    public User getByKey(String key) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ers_users WHERE user_id = ?")){
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getRow(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(getRow(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return users;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        try (Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE username = ? AND password = ?")){
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return getRow(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return null;
    }

    public boolean isUsernameAvailable(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT username FROM ers_users WHERE username = ?")){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return false;
    }

    private User getRow(ResultSet rs) throws SQLException {
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

    public User getUserByUsername(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE username = ?")){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return getRow(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return null;
    }

    public List<User> getAllActiveUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE is_active = 'TRUE'")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(getRow(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return users;
    }

    public List<User> getAllInactiveUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE is_active = 'FALSE'")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(getRow(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return users;
    }
}
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

public class ReimbursementDAO implements DAO<Reimbursement> {

    @Override
    public void save(Reimbursement reimbursement) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ers_reimbursements (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id, resolver_id, status_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, reimbursement.getReimb_id().toString());
            ps.setBigDecimal(2, reimbursement.getAmount());
            ps.setTimestamp(3, reimbursement.getSubmitted());
            ps.setTimestamp(4, reimbursement.getResolved());
            ps.setString(5, reimbursement.getDescription());
            ps.setBytes(6, reimbursement.getReceipt());
            ps.setString(7, reimbursement.getPayment_id().toString());
            ps.setString(8, reimbursement.getAuthor_id().toString());
            ps.setString(9, reimbursement.getResolver_id().toString());
            ps.setString(10, reimbursement.getStatus_id().toString());
            ps.setString(11, reimbursement.getType_id().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    public void setStatus(Reimbursement reimbursement) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE ers_reimbursements SET resolved = ?, resolver_id = ?, status_id = ? WHERE reimb_id = ?")) {
            ps.setTimestamp(1, reimbursement.getResolved());
            ps.setString(2, reimbursement.getResolver_id().toString());
            ps.setString(3, reimbursement.getStatus_id().toString());
            ps.setString(4, reimbursement.getReimb_id().toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(Reimbursement obj) {throw new InvalidSQLException("Not yet implemented.");}

    @Override
    public void delete(UUID id) {throw new InvalidSQLException("Not yet implemented.");}

    private Reimbursement getRow(ResultSet rs) throws SQLException {
        return new Reimbursement(
                UUID.fromString(rs.getString("reimb_id")),
                rs.getBigDecimal("amount"),
                rs.getTimestamp("submitted"),
                rs.getTimestamp("resolved"),
                rs.getString("description"),
                rs.getBytes("receipt"),
                UUID.fromString(rs.getString("payment_id")),
                UUID.fromString(rs.getString("author_id")),
                UUID.fromString(rs.getString("resolver_id")),
                ReimbursementStatus.valueOf(rs.getString("status_id")),
                ReimbursementType.valueOf(rs.getString("type_id"))
        );
    }
    
    @Override
    public Reimbursement getByKey(String key) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ers_reimbursements WHERE reimb_id = ?")) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return getRow(rs);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
    }

    @Override
    public List<Reimbursement> getAll() {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getByType(ReimbursementType type) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE type_id = ?")) {
            ps.setString(1, type.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getByStatus(ReimbursementStatus status) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE status_id = ?")) {
            ps.setString(1, status.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getByTypeAndStatus(ReimbursementType type, ReimbursementStatus status) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE type_id = ? AND status_id = ?")) {
            ps.setString(1, type.toString());
            ps.setString(2, status.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getByUser(User user, ReimbursementStatus status) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE author_id AND status_id = ?")) {
            ps.setString(1, user.getUserID().toString());
            ps.setString(2, status.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }
}
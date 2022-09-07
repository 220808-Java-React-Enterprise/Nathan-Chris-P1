package com.revature.daos;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.custom_exceptions.InvalidSQLException;
import com.revature.utils.database.ConnectionFactory;

import java.sql.*;
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
            if(reimbursement.getResolver_id() == null){
                ps.setNull(9, Types.VARCHAR);
            }else{
                ps.setString(9, reimbursement.getResolver_id().toString());
            }
            ps.setString(10, reimbursement.getStatus_id().name());
            ps.setString(11, reimbursement.getType_id().name());
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
            ps.setString(3, reimbursement.getStatus_id().name());
            ps.setString(4, reimbursement.getReimb_id().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(Reimbursement reimbursement) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE ers_reimbursements SET amount = ?, submitted = ?, resolved = ?, description = ?, receipt = ?, payment_id = ?, author_id = ?, resolver_id = ?, status_id = ?, type_id = ? WHERE reimb_id = ?")) {
            ps.setBigDecimal(1, reimbursement.getAmount());
            ps.setTimestamp(2, reimbursement.getSubmitted());
            ps.setTimestamp(3, reimbursement.getResolved());
            ps.setString(4, reimbursement.getDescription());
            ps.setBytes(5, reimbursement.getReceipt());
            ps.setString(6, reimbursement.getPayment_id().toString());
            ps.setString(7, reimbursement.getAuthor_id().toString());
            if(reimbursement.getResolver_id() == null){
                ps.setNull(8, Types.VARCHAR);
            }else{
                ps.setString(8, reimbursement.getResolver_id().toString());
            }
            ps.setString(9, reimbursement.getStatus_id().name());
            ps.setString(10, reimbursement.getType_id().name());
            ps.setString(11, reimbursement.getReimb_id().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

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
                rs.getString("payment_id") != null ? UUID.fromString(rs.getString("payment_id")) : null,
                UUID.fromString(rs.getString("author_id")),
                rs.getString("resolver_id") != null ? UUID.fromString(rs.getString("resolver_id")) : null,
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
            ps.setString(1, type.name());
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
            ps.setString(1, status.name());
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
            ps.setString(1, type.name());
            ps.setString(2, status.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getByUser(User user) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE author_id = ?")) {
            ps.setString(1, user.getUserID().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }

    public List<Reimbursement> getByManager(User user) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE resolver_id = ? AND status_id = ?")) {
            ps.setString(1, user.getUserID().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reimbursements.add(getRow(rs));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to read from the database.");
        }
        return reimbursements;
    }
    public List<Reimbursement> getByManagerAndType(User user, ReimbursementType type) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE resolver_id = ?" + 
                                                                 (type != null ? " AND type_id = ?" : ""))) {
            ps.setString(1, user.getUserID().toString());
            if (type != null) ps.setString(2, type.name());
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
package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

/*
 * For DAOs, only CR from CRUD should be accessible.
 */
public class AccountDAO {

    /**
     * Creates an account and inserts it into the database if the username contained in the
     * Account does not already exist in the database. 
     * @param account the account to be registered
     * @return the registered account if successful, null otherwise.
     */
    public Account registerAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            // SQL logic
            if (!accountExists(account.getUsername(), 0, true)) {
                String sql_2 = "insert into account(username, password) values (?, ?)";
                PreparedStatement preparedStatement_2 = connection.prepareStatement(sql_2, Statement.RETURN_GENERATED_KEYS);
                preparedStatement_2.setString(1, account.getUsername());
                preparedStatement_2.setString(2, account.getPassword());
                int updatedRows = preparedStatement_2.executeUpdate();
                if (updatedRows == 1) {
                    ResultSet pkeyResultSet = preparedStatement_2.getGeneratedKeys();
                    if(pkeyResultSet.next()){
                        int generated_account_id = (int) pkeyResultSet.getLong(1);
                        System.out.println(generated_account_id);
                        return new Account(generated_account_id, account.getUsername(), account.getPassword());
                    }
                }
            } else {
                return null;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Logs into an account by checking if the provided account has the correct credentials.
     * Returns the account if the account is authenticated, else returns null. 
     * @param account the account to be logged into
     * @return the registered account if successful, null otherwise.
     */
    public Account logIntoAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            // SQL logic
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.isBeforeFirst()) { 
                return null;
            }
            if (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Checks if the username exists in the database or not.
     * @param username the username to be checked for whether it is in the database or not
     * @return true if the username exists in the database, false otherwise
     */
    public boolean accountExists(String username, int account_id, boolean byUsername) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            // SQL logic
            StringBuilder sqlSB = new StringBuilder("select * from account where ");
            if (byUsername) {
                sqlSB.append("username = ?");
            } else {
                sqlSB.append("account_id = ?");
            }
            String sql = sqlSB.toString();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (byUsername) {
                sqlSB.append("username = ?");
                preparedStatement.setString(1, username);
            } else {
                sqlSB.append("account_id = ?");
                preparedStatement.setInt(1, account_id);
            }
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

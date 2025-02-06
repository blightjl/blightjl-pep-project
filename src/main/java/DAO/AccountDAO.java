package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * For DAOs, only CR from CRUD should be accessible.
 */
public class AccountDAO {

    public Account registerAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        /*
            create table account (
                account_id int primary key auto_increment,
                username varchar(255) unique,
                password varchar(255)
            );
        */
        
        try {
            // SQL logic
            String sql = "select * from account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.isBeforeFirst()) {
                String sql_2 = "insert into account(username, password) values (?, ?)";
                PreparedStatement preparedStatement_2 = connection.prepareStatement(sql_2, Statement.RETURN_GENERATED_KEYS);
                preparedStatement_2.setString(1, account.getUsername());
                preparedStatement_2.setString(2, account.getPassword());
                int updatedRows = preparedStatement_2.executeUpdate();
                if (updatedRows != 1) {
                    System.out.println("Failed to register an account!");
                } else {
                    System.out.println("Registered an account!");
                    ResultSet pkeyResultSet = preparedStatement_2.getGeneratedKeys();
                    System.out.println("before pkeyresultset!");
                    if(pkeyResultSet.next()){
                        int generated_account_id = (int) pkeyResultSet.getLong(1);
                        System.out.println(generated_account_id);
                        return new Account(generated_account_id, account.getUsername(), account.getPassword());
                    }
                }
                System.out.println("after pkeyresultset!");
            } else {
                return null;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account logIntoAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        /*
            create table account (
                account_id int primary key auto_increment,
                username varchar(255) unique,
                password varchar(255)
            );
        */
        
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

    public boolean usernameExists(int account_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            // SQL logic
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
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

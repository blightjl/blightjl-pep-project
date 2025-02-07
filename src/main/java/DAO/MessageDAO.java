package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * For DAOs, only CR from CRUD should be accessible.
 * 
 * The MessageDAO class that contains methods related to Messages to directly manipulate the database.
 */
public class MessageDAO {

    /**
     * Retrieves a list of all messages in the database through a prepared statement. 
     * @return the list of messages in the database.
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "select * from message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Retrieves a message by a message id in the database through a prepared statement. 
     * @return the message associated with the message_id if it exists, null otherwise.
     */
    public Message getMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a list of all messages in the database associated with an account id 
     * through a prepared statement. 
     * @return a list of messages made by account id in the database.
     */
    public List<Message> getAllMessagesByAccountID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            // SQL logic
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            System.out.println(account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Inserts a message into the database through a prepared statement. 
     * @param account the message object to be inserted {does not have a message id}.
     * @return the message object with the generated message id if inserting .
     *         is successful, null otherwise.
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());

            preparedStatement.setString(2, message.getMessage_text());

            preparedStatement.setFloat(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Updates a message in the database with the content of another message object.
     * @param message_id the message id of the message object to be updated.
     * @param message the message object that contains the new message content.
     * @return the updated message object with the new message content if successful, null otherwise.
     */
    public Message updateMessage(int message_id, Message message){
        Message messageToBeUpdated = this.getMessageByID(message_id);
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);

            int updatedRow = preparedStatement.executeUpdate();
            if(updatedRow == 1){
                System.out.println(new Message(message_id, messageToBeUpdated.getPosted_by(), message.getMessage_text(), messageToBeUpdated.getTime_posted_epoch()));
                return new Message(message_id, messageToBeUpdated.getPosted_by(), message.getMessage_text(), messageToBeUpdated.getTime_posted_epoch());
            } else {
                return null;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a message identified by the message id in the database.
     * @param message_id the message id of the message object to be deleted.
     * @return the deleted message object if successful, null otherwise.
     */
    public Message deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        Message messageToBeDeleted = this.getMessageByID(message_id);
        try {
            String sql = "delete from message where message_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            int deletedRow = preparedStatement.executeUpdate();
            System.out.println(deletedRow);
            if(deletedRow == 1){
                return messageToBeDeleted;
            } else {
                return null;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

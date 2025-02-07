package Service;

import DAO.MessageDAO;
import Model.Message;


import java.util.List;

/*
 * Sits between the Controller and the DAO. It calls DAO objects.
 * 
 * The Message Service class which contains the business logic to conducts input checks 
 * and calls methods on the message DAO.
 */
public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * This method conducts input checks on message to be added such that it is in the accepted
     * range and the account id that it is associated with exists in the database. If the message
     * passes these checks the method calls the insert message onto the message DAO.
     *
     * @param message the message to be added to the database
     * @return the added message
     */
    public Message addMessage(Message message) {
        // check to make sure the message is in the right format
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        }
        // check the user attempting to message exists
        if (!this.accountService.accountExists("", message.getPosted_by(), false)) {
            return null;
        }

        return this.messageDAO.insertMessage(message);
    }

    /**
     * This method calls the getAllMessages() method of the message DAO to retrieve all messages.
     *
     * @return the list of messages returned by the message DAO.
     */
    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    /**
     * This method calls the getMessageByID() to retrieve the message that is associated with the message id.
     *
     * @param message the message to be added to the database
     * @return the added message if successful, null otherwise
     */
    public Message getMessageByID(int message_id) {
        return this.messageDAO.getMessageByID(message_id);
    }

    /**
     * This makes sure that the message text that is contained in the new message object is 
     * within the acceptable range and that the message to be updated exists. If it passes
     * these checks, the updateMessage() method is called on the message DAO.
     *
     * @param message_id the id of the message to be updated
     * @param message the message object that contains the new message replacement
     * @return the updated message if successful, null otherwise
     */
    public Message updateMessage(int message_id, Message message) {
        // check to make sure the message is in the right format
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        }
        // check the messagemessage exists
        Message messageToBeUpdated = this.messageDAO.getMessageByID(message_id);
        if (messageToBeUpdated != null) { 
            this.messageDAO.updateMessage(message_id, message);
            return this.messageDAO.getMessageByID(message_id);
        }
        return null;
    }

    /**
     * This method calls the deleteMessage() on the messageDAO to delete a message identified by
     * the message id parameter.
     *
     * @param message_id the message id of the message to be deleted
     * @return the deleted message if successful, null otherwise
     */
    public Message deleteMessage(int message_id) {
        return this.messageDAO.deleteMessage(message_id);
    }

    /**
     * This method calls the getAllMessagesByAccountID() on the message DAO to retrieve all
     * messages posted by the account id.
     *
     * @param account_id the account it to filter the list of messages by
     * @return a list of messages made by the account id
     */
    public List<Message> getMessagesByAccountID(int account_id) {
        return this.messageDAO.getAllMessagesByAccountID(account_id);
    }
}

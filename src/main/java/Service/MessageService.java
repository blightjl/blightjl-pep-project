package Service;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;

import Service.AccountService;

import java.util.List;

/*
 * Sits between the Controller and the DAO. It calls DAO objects.
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

    public Message addMessage(Message message) {
        // check to make sure the message is in the right format
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        }
        // check the user attempting to message exists
        if (!this.accountService.usernameExists(message.getPosted_by())) {
            return null;
        }

        return this.messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return this.messageDAO.getMessageByID(message_id);
    }

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

    public Message deleteMessage(int message_id) {
        System.out.println("DELETING A MESSAGE");
        return this.messageDAO.deleteMessage(message_id);
    }

    public List<Message> getMessagesByAccountID(int account_id) {
        return this.messageDAO.getAllMessagesByAccountID(account_id);
    }
}

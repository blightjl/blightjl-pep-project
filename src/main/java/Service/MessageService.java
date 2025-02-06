package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

/*
 * Sits between the Controller and the DAO. It calls DAO objects.
 */
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        return this.messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return this.messageDAO.getMessageByID(message_id);
    }

    public Message updateMessage(Message message) {
        Message messageToBeUpdated = this.messageDAO.getMessageByID(message.getMessage_id());
        if (messageToBeUpdated != null) {
            this.messageDAO.updateMessage(message);
            return this.messageDAO.getMessageByID(message.getMessage_id());
        }
        return null;
    }

    public Message deleteMessage(int message_id) {
        return this.messageDAO.deleteMessage(message_id);
    }

    public List<Message> getMessagesByAccountID(int account_id) {
        return this.messageDAO.getAllMessagesByAccountID(account_id);
    }
}

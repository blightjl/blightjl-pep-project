package Service;

import DAO.MessageDAO;
import Model.Message;

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
        return message;
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return this.messageDAO.getMessageByID();
    }

    public Message updateMessageByID(int message_id) {
        return this.messageDAO.updateMessageByID();
    }

    public Message deleteMessageByID(int message_id) {
        return this.messageDAO.deleteMessageByID();
    }
}

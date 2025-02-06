package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);

        /*
         * Ordered top-down in the order of CRUD operations.
         */
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::loginAccountHandler);

        /*
         * Ordered top-down in the order of CRUD operations.
         */
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    // TODO: CONTROLLER IS THE ONE SENDING STATUS CODES

    /*
    public int registerAccount(Account account) {
        Account registeredAccount = this.accountDAO.registerAccount(account);
        if (registeredAccount != null) {
            return 200;
        }
        return 400;
    }

    public int logIntoAccount(Account account) {
        Account loggedAccount = this.accountDAO.registerAccount(account);
        if (loggedAccount != null) {
            return 200;
        }
        return 401;
    }
     */

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for posting an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredAccount = this.accountService.registerAccount(account);
        if (registeredAccount != null) {
            context.json(mapper.writeValueAsString(registeredAccount));
            context.status(200);
        } else {
            context.status(400);
        }
    }

    /**
     * This is the handler for logging into an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedAccount = this.accountService.logIntoAccount(account);
        if (loggedAccount != null) {
            context.json(mapper.writeValueAsString(loggedAccount));
            context.status(200);
        } else {
            context.status(401);
        }
    }

    /**
     * This is the handler for posting a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message sentMessage = this.messageService.addMessage(message);
        if (sentMessage != null) {
            context.json(mapper.writeValueAsString(sentMessage));
            context.status(200);
        } else {
            context.status(400);
        }
    }

    /**
     * This is the handler for retrieving all messages.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = this.messageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }

    /**
     * This is the handler for retrieving a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message sentMessage = this.messageService.getMessageByID(message_id);
        if (sentMessage != null) {
            context.json(mapper.writeValueAsString(sentMessage));
        }
        context.status(200);
    }

    /**
     * This is the handler for retrieving all messages from an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesByAccountHandler(Context context) throws JsonProcessingException {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messagesByAccountID = this.messageService.getMessagesByAccountID(account_id);
        context.json(messagesByAccountID);
        context.status(200);
    }

    /**
     * This is the handler for deleting a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = this.messageService.deleteMessage(message_id);
        if (deletedMessage != null) {
            context.json(mapper.writeValueAsString(deletedMessage));
        }
        context.status(200);
    }

    /**
     * This is the handler for updating a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = this.messageService.updateMessage(message_id, message);
        if (updatedMessage != null) {
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        } else {
            context.status(400);
        }

    }
}
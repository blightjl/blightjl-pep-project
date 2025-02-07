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

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * The Post Account Handler that handles attempts of registering a new account on the endpoint /register.
     * If the Account Service successfully registers an account, the API will set the response body to the JSON
     * object of the registered account and returns a status code of 200. Else the Account Service fails to 
     * register an account {when it returns a null}, the API will return a status code of 400 {a client error}.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
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
     * The Login Account Handler that handles attempts of logging into an account on the endpoint /login.
     * If the Account Service successfully logins into an account when it returns an Account object, 
     * the API will set the response body to the JSON object of the logged-in account and returns 
     * a status code of 200. Else the Account Service fails to log into an account {when it returns a null},
     *  the API will return a status code of 401 {Unauthorized}.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
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
     * The Post Message Handler that handles posting messages on the endpoint /messages.
     * If the Message Service successfully posts a message {when it returns a Message object}, 
     * the API will set the response body to the JSON object of the posted message and returns 
     * a status code of 200. Else the Message Service fails to post an account {when it returns a null},
     *  the API will return a status code of 400 {a client error}.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
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
     * The Get All Messages Handler that handles retrieving all messages on the endpoint /messages.
     * The Message Service will return all messages that is stored in the database, and the context
     * will set the response body to the json object of the list of messages retrieved. This method 
     * will always return a status code of 200.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = this.messageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }

    /**
     * The Get Message Handler that handles retrieving a message by message id based on the
     * path parameter on the endpoint /messages/{message_id}. If the Message Service successfully
     * retrieves a message by the message id{when it returns a Message object}, the API will set 
     * the response body to the JSON object of the retrieved message. If no message is
     * linked to the message id, the response body will be empty. This method will always set 
     * the a status code to 200.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
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
     * The Get All Messages Handler that handles retrieving all messages of an account on 
     * the endpoint /messages/{account_id}/messages. The Message Service will return all messages that have the
     * posted_by variable that matches with the account_id path parameter, and the context
     * will set the response body to the json object of the list of messages retrieved. This method will
     * always set the a status code to 200.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
     */
    private void getAllMessagesByAccountHandler(Context context) throws JsonProcessingException {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messagesByAccountID = this.messageService.getMessagesByAccountID(account_id);
        context.json(messagesByAccountID);
        context.status(200);
    }

    /**
     * The Delete Message Handler that handles deleting a message by message id based on the
     * path parameter on the endpoint /messages/{message_id}. If the Message Service successfully 
     * retrieves a message by the message id {when it returns a Message object}, the API will set 
     * the response body to the JSON object of the deleted message. If no message is
     * linked to the message id and nothing is deleted, the response body will be empty. This method will
     * always set the a status code to 200.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
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
     * The Update Message Handler that handles updating a message {patching} by message id based on the
     * path parameter and the request body's new message on the endpoint /messages/{message_id}. If 
     * the Message Service successfully returns a message by the message id {when it returns a Message object}, 
     * the API will set the response body to the JSON object of the returned message as it means update was 
     * successful and the status will be set to 200. If no message is linked to the message id and update is 
     * made, the response body will be empty and the status code will be set to 400.
     * @param context the Javalin context object that manages the HTTP request and response.
     * @throws JsonProcessingException is thrown if JSON fails to convert to an object.
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
package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
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
        app.get("/accounts/{account_id}", this::getAllMessagesByAccountHandler);
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
     * This is the handler for posting an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for logging into an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginAccountHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for posting a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for retrieving all messages.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for retrieving a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for retrieving all messages from an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesByAccountHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for deleting a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is the handler for updating a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageHandler(Context context) {
        context.json("sample text");
    }
}
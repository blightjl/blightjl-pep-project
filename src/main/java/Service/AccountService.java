package Service;

import DAO.AccountDAO;
import Model.Account;

/*
 * Sits between the Controller and the DAO. It calls DAO objects.
 * 
 * The Account Service class which contains the business logic to conducts input checks 
 * and calls methods on the account DAO.
 */
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * This method adds an account with the given credential if the username and password credential provided is of
     * the appropriate length and the username does not exist in the database.
     *
     * @param account the transient account object {which does not contain the account id} only the username and password
     * @return the persisted account object if registering is successful, null orhterwise
     */
    public Account registerAccount(Account account) {
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return null;
        }
        if (accountExists(account.getUsername(), 0, true)) {
            return null;
        }
        return this.accountDAO.registerAccount(account);
    }

    /**
     * This method attempts to log in with an account with the given credential by calling the method
     * on the account DAO.
     *
     * @param account the account to be authenticated
     * @return the account that was logged in if login was successful, null otherwise
     */
    public Account logIntoAccount(Account account) {
        return this.accountDAO.logIntoAccount(account);
    }

    /**
     * This method checks whether an account exists or not either by the provided username or 
     * account_id based on the boolean value byUsername. 
     * {username if byUsername is true, account_id if byUsername is false}
     *
     * @param username the username to be checked for existence in the database if byUsername is true
     * @param account_id the account_id to be checked for existence in the database if byUsername is false
     * @param byUsername boolean value that determines which parameter to determine account existence with {username or account_id}
     * @return true if account was found, false otherwise
     */
    public boolean accountExists(String username, int account_id, boolean byUsername) {
        return this.accountDAO.accountExists(username, account_id, byUsername);
    }
}

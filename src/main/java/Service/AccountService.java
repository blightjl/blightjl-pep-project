package Service;

import DAO.AccountDAO;
import Model.Account;

/*
 * Sits between the Controller and the DAO. It calls DAO objects.
 */
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

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

    
}

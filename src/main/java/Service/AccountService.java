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

    public Account registerAccount(Account account) {
        return this.accountDAO.registerAccount(account);
    }

    public Account logIntoAccount(Account account) {
        return this.accountDAO.logIntoAccount(account);
    }

    
}

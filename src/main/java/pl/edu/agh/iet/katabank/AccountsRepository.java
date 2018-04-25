package pl.edu.agh.iet.katabank;

import java.util.Set;

public interface AccountsRepository {

    void addAccount(Account account);

    Set<Account> findAccountsForCustomer(Customer customer);
}

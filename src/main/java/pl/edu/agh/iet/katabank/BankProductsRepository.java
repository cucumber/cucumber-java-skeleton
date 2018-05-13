package pl.edu.agh.iet.katabank;

import java.util.Set;

public interface BankProductsRepository {

    void addAccount (Account account);

    Set<Account> findAccountsForCustomer (Customer customer);

}

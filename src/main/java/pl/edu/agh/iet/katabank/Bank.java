package pl.edu.agh.iet.katabank;

import java.util.Set;

public class Bank {

    private AccountsRepository accountsRepository;

    public Bank(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Set<Account> getAccountsForCustomer(Customer customer) {
        return accountsRepository.findAccountsForCustomer(customer);
    }
}

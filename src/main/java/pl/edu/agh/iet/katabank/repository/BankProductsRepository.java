package pl.edu.agh.iet.katabank.repository;

import pl.edu.agh.iet.katabank.Customer;
import pl.edu.agh.iet.katabank.bankproduct.Account;
import pl.edu.agh.iet.katabank.bankproduct.Deposit;

import java.util.Set;

public interface BankProductsRepository {

    void addAccount (Account account);

    Set<Account> findAccountsForCustomer (Customer customer);

    void addDeposit (Deposit deposit);

    Set<Deposit> findDepositsForCustomer (Customer customer);

}

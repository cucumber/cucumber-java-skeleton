package pl.edu.agh.iet.katabank;

import pl.edu.agh.iet.katabank.bankproduct.Account;
import pl.edu.agh.iet.katabank.bankproduct.Deposit;
import pl.edu.agh.iet.katabank.repository.BankProductsRepository;

import java.math.BigDecimal;
import java.util.Set;

public class Bank {

    private static final String ERROR_MESSAGE_DEPOSIT = "Customer cannot deposit money to others account.";
    private static final String ERROR_MESSAGE_WITHDRAW = "Customer cannot withdraw money from others account.";
    private static final String ERROR_MESSAGE_TRANSFER = "Customer cannot transfer money from others account.";
    private static final String ERROR_MESSAGE_OPEN_DEPOSIT = "Customer cannot open deposit from others account.";

    private BankProductsRepository bankProductsRepository;

    public Bank(BankProductsRepository bankProductsRepository) {
        this.bankProductsRepository = bankProductsRepository;
    }

    public Set<Account> getAccountsForCustomer(Customer customer) {
        return bankProductsRepository.findAccountsForCustomer(customer);
    }

    public void deposit(Customer customer, Account account, BigDecimal depositAmount) {
        checkOperationNotAllowed(customer, account, ERROR_MESSAGE_DEPOSIT);
        account.deposit(depositAmount);
    }

    public void withdraw(Customer customer, Account account, BigDecimal withdrawAmount) {
        checkOperationNotAllowed(customer, account, ERROR_MESSAGE_WITHDRAW);
        account.withdraw(withdrawAmount);
    }

    public void transfer(Customer customer, Account customersAccount, Account targetAccount, BigDecimal transferAmount) {
        checkOperationNotAllowed(customer, customersAccount, ERROR_MESSAGE_TRANSFER);
        customersAccount.transfer(targetAccount, transferAmount);
    }

    private void checkOperationNotAllowed(Customer customer, Account account, String message) {
        if (!getAccountsForCustomer(customer).contains(account)) {
            throw new RuntimeException(message);
        }
    }

    public Set<Deposit> getDepositsForCustomer(Customer customer) {
        return bankProductsRepository.findDepositsForCustomer(customer);
    }

    public Deposit openDeposit (Customer customer, Account account, BigDecimal depositBalance){
        checkOperationNotAllowed(customer, account, ERROR_MESSAGE_OPEN_DEPOSIT);
        return new Deposit(account, depositBalance);
    }
}

package pl.edu.agh.iet.katabank.steps;

import cucumber.api.java8.En;
import pl.edu.agh.iet.katabank.Bank;
import pl.edu.agh.iet.katabank.Customer;
import pl.edu.agh.iet.katabank.bankproduct.Account;
import pl.edu.agh.iet.katabank.bankproduct.Deposit;
import pl.edu.agh.iet.katabank.bankproduct.deposittype.DepositType;
import pl.edu.agh.iet.katabank.bankproduct.deposittype.MonthlyDepositType;
import pl.edu.agh.iet.katabank.repository.BankProductsRepository;
import pl.edu.agh.iet.katabank.repository.InMemoryBankProductsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositManagementSteps implements En {

    private BankProductsRepository bankProductsRepository;
    private Customer customer;
    private Bank bank;
    private Account account;
    private Deposit deposit;
    private Set<Deposit> customerDeposits;
    private LocalDate date;
    private BigDecimal amount;
    private DepositType depositType;

    public DepositManagementSteps() {

        Given("^there is a bank with account and customer$", () -> {
            bankProductsRepository = new InMemoryBankProductsRepository();
            bank = new Bank(bankProductsRepository);
            customer = new Customer();
        });

        Given("^there is a default deposit type$", ()
                -> depositType = new MonthlyDepositType(12, BigDecimal.ONE));

        Given("^a customer has an account with balance (\\d+)$", (Integer balance) -> {
            account = new Account(customer);
            account.setBalance(new BigDecimal(balance));
            bankProductsRepository.addAccount(account);
        });

        When("^he opens a deposit with balance (\\d+)$", (Integer depositBalance) -> {
            deposit = bank.openDeposit(customer, account, new BigDecimal(depositBalance), depositType);
            bankProductsRepository.addDeposit(deposit);
        });

        Then("^he owns a deposit with balance (\\d+)$", (Integer depositBalance) -> {
            customerDeposits = bank.getDepositsForCustomer(customer);
            assertThat(customerDeposits).contains(deposit);
            assertThat(deposit.getBalance()).isEqualByComparingTo(new BigDecimal(depositBalance));
        });

        And("^the account has balance (\\d+)$", (Integer accountNewBalance)
                -> assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal(accountNewBalance)));

        Given("^a customer opened a deposit for a period of one year$", () -> {
            account = new Account(customer);
            amount = new BigDecimal("10");
            account.setBalance(amount);
            date = LocalDate.now();
            deposit = new Deposit(account, amount, date, depositType);
        });

        When("^one year has passed$", () -> {
            assertThat(account.getBalance()).isLessThan(amount);
            assertThat(deposit.getBalance()).isEqualByComparingTo(amount);
            date = date.plusMonths(12);
        });

        Then("^the money is transferred back to the account the funds were taken from$", () -> {
            deposit.closeDeposit(date);
            assertThat(account.getBalance()).isGreaterThanOrEqualTo(amount);
            assertThat(deposit.getBalance()).isZero();
            assertThat(deposit.isOpen()).isFalse();
        });

        Given("^bank offers a deposit for a period of (\\d+) months with yearly interest rate (\\d+)%$",
                (Integer durationInMonths, Integer interestRate)
                        -> depositType = new MonthlyDepositType(durationInMonths, new BigDecimal(interestRate)));

        And("^customer opens that deposit with funds (\\d+)$", (Integer initialBalance) -> {
            amount = new BigDecimal(initialBalance);
            account = new Account(customer);
            account.setBalance(amount);
            date = LocalDate.now();
            deposit = new Deposit(account, amount, date, depositType);
        });

        When("^a termination date has passed$", () -> {
            date = deposit.getCloseDate();
            deposit.closeDeposit(date);
        });

        Then("^(\\d+) is transferred back to his account$",
                (Integer newBalance) ->
                        assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal(newBalance)));


    }
}

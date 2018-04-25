package pl.edu.agh.iet.katabank.steps;

import cucumber.api.java8.En;
import pl.edu.agh.iet.katabank.*;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountManagementSteps implements En {

    private AccountsRepository accountsRepository;
    private Customer customer;
    private Bank bank;
    private Account firstAccount, secondAccount;
    private Set<Account> customerAccounts;

    public AccountManagementSteps() {

        Given("^there is a bank with account$", () -> {
            accountsRepository = new InMemoryAccountsRepository();
            bank = new Bank(accountsRepository);
        });

        Given("^there is a customer$",
                () -> customer = new Customer());

        Given("^a customer has two accounts open$", () -> {
            // create first account for customer
            firstAccount = new Account(customer);
            accountsRepository.addAccount(firstAccount);

            // create second account for customer
            secondAccount = new Account(customer);
            accountsRepository.addAccount(secondAccount);
        });

        When("^he lists his accounts$",
                () -> customerAccounts = bank.getAccountsForCustomer(customer));

        Then("^only those accounts are on the list$",
                () -> assertThat(customerAccounts)
                        .containsExactlyInAnyOrder(firstAccount, secondAccount));

        Given("^a customer wants to open an account$", () -> customer = new Customer());

        When("^his account is created$", () -> {
            firstAccount = new Account(customer);
            accountsRepository.addAccount(firstAccount);
        });

        Then("^there is a new account on his account list$", () -> {
            customerAccounts = bank.getAccountsForCustomer(customer);
            assertThat(customerAccounts).contains(firstAccount);
        });
      
        And("^the balance on this account is (\\d+)$",
                (Integer balance) -> assertThat(firstAccount.getBalance()).isEqualByComparingTo(new BigDecimal(balance)));

        Given("^balance on the account is (\\d+)$", (Integer initialBalance) -> {
            firstAccount = new Account(new Customer());
            firstAccount.setBalance(new BigDecimal(initialBalance));
        });

        When("^customer withdraws (\\d+) from this account$", (Integer amount) -> firstAccount.withdraw(new BigDecimal(amount)));

        Then("^balance on this account is (\\d+)$",
                (Integer balance) -> assertThat(firstAccount.getBalance()).isEqualByComparingTo(new BigDecimal(balance)));

    }
}

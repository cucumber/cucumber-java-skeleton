package pl.edu.agh.iet.katabank.steps;

import cucumber.api.java8.En;
import pl.edu.agh.iet.katabank.Bank;
import pl.edu.agh.iet.katabank.Customer;
import pl.edu.agh.iet.katabank.bankproduct.Account;
import pl.edu.agh.iet.katabank.bankproduct.Deposit;
import pl.edu.agh.iet.katabank.bankproduct.amount.DepositPayment;
import pl.edu.agh.iet.katabank.bankproduct.amount.Payment;
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails;
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.InterestPolicy;
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.MonthlyInterestPolicy;
import pl.edu.agh.iet.katabank.repository.BankProductsRepository;
import pl.edu.agh.iet.katabank.repository.InMemoryBankProductsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails.DurationType.MONTHS;

public class DepositManagementSteps implements En {

    private BankProductsRepository bankProductsRepository;
    private Customer customer;
    private Bank bank;
    private Account account;
    private Deposit deposit;
    private Set<Deposit> customerDeposits;
    private LocalDate date;
    private BigDecimal amount;
    private InterestPolicy interestPolicy;
    private DepositDurationDetails durationDetails;

    public DepositManagementSteps() {

        Given("^there is a bank with account and customer$", () -> {
            bankProductsRepository = new InMemoryBankProductsRepository();
            bank = new Bank(bankProductsRepository);
            customer = new Customer();
            account = new Account(customer);
            bankProductsRepository.addAccount(account);
        });

        Given("^there is a default interest policy and deposit duration$", () -> {
            interestPolicy = new MonthlyInterestPolicy(new BigDecimal(10));
            durationDetails = new DepositDurationDetails(12, MONTHS);
        });

        Given("^a customer has an account with balance (\\d+)$", (Integer balance) -> {
            account.setBalance(new BigDecimal(balance));
        });

        When("^he opens a deposit with balance (\\d+)$", (Integer depositBalance) -> {
            deposit = bank.openDeposit(customer, account, new BigDecimal(depositBalance), durationDetails, interestPolicy);
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
            amount = new BigDecimal("10");
            account.setBalance(amount);
            date = LocalDate.now();
            deposit = bank.openDeposit(customer, account, amount, durationDetails, interestPolicy);
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
                (Integer durationInMonths, Integer interestRate) -> {
                    interestPolicy = new MonthlyInterestPolicy(new BigDecimal(interestRate));
                    durationDetails = new DepositDurationDetails(durationInMonths, MONTHS);

                });

        And("^customer opens that deposit with funds (\\d+)$", (Integer initialBalance) -> {
            amount = new BigDecimal(initialBalance);
            account.setBalance(amount);
            date = LocalDate.now();
            deposit = bank.openDeposit(customer, account, amount, durationDetails, interestPolicy);
        });

        When("^a termination date has passed$", () -> {
            date = deposit.getCloseDate();
            deposit.closeDeposit(date);
        });

        Then("^(\\d+) is transferred back to his account$",
                (Integer newBalance) ->
                        assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal(newBalance)));

        Given("^there is a customer with a deposit opened for period of (\\d+) months with interest rate of (\\d+)% and (\\d+) amount$",
                (Integer durationInMonths, Integer interestRate, Integer depositBalance) -> {
                    interestPolicy = new MonthlyInterestPolicy(new BigDecimal(interestRate));
                    durationDetails = new DepositDurationDetails(durationInMonths, MONTHS);
                    amount = new BigDecimal(depositBalance);
                    account.setBalance(amount);
                    deposit = bank.openDeposit(customer, account, amount, durationDetails, interestPolicy);
                    bankProductsRepository.addDeposit(deposit);
                });

        When("^after (\\d+) months he transfers new funds of amount (\\d+) to the existing deposit with interest higher by (.+)%$",
                (Integer periodInMonths, Integer newFundsAmount, String interestIncrease) -> {
                    InterestPolicy secondInterestPolicy = new MonthlyInterestPolicy(interestPolicy.getYearlyInterestRatePercent().add(new BigDecimal(interestIncrease)));
                    Payment payment = new DepositPayment(new BigDecimal(newFundsAmount), LocalDate.now().plusMonths(periodInMonths));
                    deposit.addPayment(payment, secondInterestPolicy);
                });

        Then("^the interest rate for these funds is (.+)% greater than the original interest rate$", (String interestIncrease) -> {
            List<BigDecimal> depositInterestRates = deposit.getInterestRates();
            assertThat(depositInterestRates.get(1).subtract(depositInterestRates.get(0))).isEqualByComparingTo(new BigDecimal(interestIncrease));
        });

        And("^the interest for this funds is proportional to the deposit time left and equals (.+)$", (String balanceWithInterest) -> {
            deposit.closeDeposit(deposit.getCloseDate());
            assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal(balanceWithInterest));
        });


    }
}

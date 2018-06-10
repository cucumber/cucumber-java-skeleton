package pl.edu.agh.iet.katabank

import pl.edu.agh.iet.katabank.bankproduct.Account
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.InterestPolicy
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.MonthlyInterestPolicy
import pl.edu.agh.iet.katabank.repository.BankProductsRepository
import pl.edu.agh.iet.katabank.repository.InMemoryBankProductsRepository
import spock.lang.Specification

import static pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails.DurationType.MONTHS

class BankTest extends Specification {

    private final BankProductsRepository repository = new InMemoryBankProductsRepository()
    private final Bank bank = new Bank(repository)
    private final Customer customer = new Customer()
    private final InterestPolicy interestPolicy = new MonthlyInterestPolicy(10.0)
    private final DepositDurationDetails depositDurationDetails = new DepositDurationDetails(12, MONTHS)
    private Account account

    def "customer cannot withdraw money from other customer account"() {
        when:
        account = new Account(customer)
        def anotherCustomer = new Customer()
        def anotherAccount = new Account(anotherCustomer)
        repository.addAccount(account)
        repository.addAccount(anotherAccount)
        bank.withdraw(customer, anotherAccount, 9.99)

        then:
        RuntimeException ex = thrown()
        ex.message == 'Customer cannot withdraw money from others account.'

    }

    def "customer cannot transfer money from other customer account"() {
        when:
        account = new Account(customer)
        def anotherCustomer = new Customer()
        def anotherAccount = new Account(anotherCustomer)
        repository.addAccount(account)
        repository.addAccount(anotherAccount)
        bank.transfer(customer, anotherAccount, account, 9.99)

        then:
        RuntimeException ex = thrown()
        ex.message == 'Customer cannot transfer money from others account.'

    }

    def "customer cannot open deposit for other customer account"() {
        when:
        account = new Account(customer)
        def anotherCustomer = new Customer()
        def anotherAccount = new Account(anotherCustomer)
        repository.addAccount(account)
        repository.addAccount(anotherAccount)
        bank.openDeposit(customer, anotherAccount, 10.0, depositDurationDetails, interestPolicy)

        then:
        RuntimeException ex = thrown()
        ex.message == 'Customer cannot open deposit from others account.'
    }

    def "cannot open a deposit for amount greater than the account balance"() {
        given:
        account = new Account(customer)
        account.setBalance(5.0)
        repository.addAccount(account)

        when:
        bank.openDeposit(customer, account, 10.0, depositDurationDetails, interestPolicy)

        then:
        RuntimeException ex = thrown()
        ex.message == "The amount to withdraw is greater than account balance."
    }

}

package pl.edu.agh.iet.katabank

import pl.edu.agh.iet.katabank.bankproduct.Account
import pl.edu.agh.iet.katabank.repository.BankProductsRepository
import pl.edu.agh.iet.katabank.repository.InMemoryBankProductsRepository
import spock.lang.Specification

class BankTest extends Specification {

    private final BankProductsRepository repository = new InMemoryBankProductsRepository()
    private final Bank bank = new Bank(repository)
    private final Customer customer = new Customer()

    def "customer cannot withdraw money from other customer account"() {
        when:
        repository.addAccount(account)
        repository.addAccount(anotherAccount)
        bank.withdraw(customer, anotherAccount, 9.99)

        then:
        RuntimeException ex = thrown()
        ex.message == 'Customer cannot withdraw money from others account.'

        where:
        account = new Account(customer)
        anotherCustomer = new Customer()
        anotherAccount = new Account(anotherCustomer)
    }

    def "customer cannot transfer money from other customer account"() {
        when:
        repository.addAccount(account)
        repository.addAccount(anotherAccount)
        bank.transfer(customer, anotherAccount, account, 9.99)

        then:
        RuntimeException ex = thrown()
        ex.message == 'Customer cannot transfer money from others account.'

        where:
        account = new Account(customer)
        anotherCustomer = new Customer()
        anotherAccount = new Account(anotherCustomer)
    }

    def "customer cannot open deposit for other customer account"() {
        when:
        repository.addAccount(account)
        repository.addAccount(anotherAccount)
        bank.openDeposit(customer, anotherAccount, 10)

        then:
        RuntimeException ex = thrown()
        ex.message == 'Customer cannot open deposit from others account.'

        where:
        account = new Account(customer)
        anotherCustomer = new Customer()
        anotherAccount = new Account(anotherCustomer)
    }
}

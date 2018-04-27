package pl.edu.agh.iet.katabank

import spock.lang.Specification

class BankTest extends Specification {

    private final AccountsRepository repository = new InMemoryAccountsRepository()
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
}

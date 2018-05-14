package pl.edu.agh.iet.katabank

import spock.lang.Specification

class DepositTest extends Specification{

    private static final String ERROR_MESSAGE = 'Incorrect amount to process: '

    private final Customer customer = new Customer()
    private final Account account = new Account(customer)
    private BigDecimal amount

    def "two deposits created for the same account are not equal"() {
        when:
        amount =  50
        account.setBalance(200)
        def firstDeposit = new Deposit(account,amount)
        def secondDeposit = new Deposit(account, amount)

        then:
        !firstDeposit.equals(secondDeposit)
    }

    def "try to open a deposit with a negative balance"() {
        when:
        amount = -1
        account.setBalance(100)
        def deposit = new Deposit(account, amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to open a deposit with a zero balance"() {
        when:
        amount = 0
        account.setBalance(100)
        def deposit = new Deposit(account, amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to open a deposit for amount greater than the account balance"() {
        when:
        amount = 100
        account.setBalance(amount-1)
        def deposit = new Deposit(account, amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to open a deposit for a null amount"() {
        when:
        account.setBalance(100)
        def deposit = new Deposit(account, amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

}

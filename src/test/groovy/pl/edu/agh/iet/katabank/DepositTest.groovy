package pl.edu.agh.iet.katabank

import spock.lang.Specification

class DepositTest extends Specification{

    private static final String ERROR_MESSAGE = 'Incorrect amount to process: '

    private final Customer customer = new Customer()
    private final Account account = new Account(customer)

    def "two deposits created for the same account are not equal"() {

    }

    def "try to open a deposit with a negative balance"() {

    }

    def "try to open a deposit with a zero balance"() {

    }

    def "try to open a deposit for amount greater than the account balance"() {

    }

    def "try to open a deposit for a null amount"() {

    }

    def "try to open a deposit for a null account"() {

    }
}

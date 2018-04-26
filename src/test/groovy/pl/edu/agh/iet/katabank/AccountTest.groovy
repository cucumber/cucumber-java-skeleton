package pl.edu.agh.iet.katabank

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class AccountTest extends Specification {

    private static final String ERROR_MESSAGE = 'Incorrect amount to process: '

    private final Customer customer = new Customer()
    private final Account account = new Account(customer)

    def "two accounts created for the same customer are not equal"() {
        expect:
        assertThat(account).isNotEqualTo(anotherAccount)

        where:
        anotherAccount = new Account(customer)
    }

    def "try to withdraw a negative amount"() {
        when:
        def amount = -1.00
        account.withdraw(amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to withdraw zero"() {
        when:
        def amount = 0.00
        account.withdraw(amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to withdraw a null"() {
        when:
        account.withdraw(null)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + null
    }

    def "try to withdraw amount greater than account balance"() {
        setup:
        account.setBalance(100.0)

        when:
        account.withdraw(100.01)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'The amount to withdraw is greater than account balance.'
    }

    def "try to deposit a negative amount"() {
        when:
        def amount = -1.00
        account.withdraw(amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to deposit zero"() {
        when:
        def amount = 0.00
        account.withdraw(amount)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + amount
    }

    def "try to deposit a null"() {
        when:
        account.withdraw(null)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE + null
    }
}

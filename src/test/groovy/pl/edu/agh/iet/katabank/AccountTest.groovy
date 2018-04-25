package pl.edu.agh.iet.katabank

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class AccountTest extends Specification{

    private final Customer customer = new Customer()
    private final Account account = new Account(customer)

    def "two accounts created for the same customer are not equal"() {
        expect:
        assertThat(account).isNotEqualTo(anotherAccount)

        where:
        anotherAccount = new Account(customer)
    }
}

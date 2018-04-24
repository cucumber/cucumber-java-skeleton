package pl.edu.agh.iet.katabank

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class CustomerTest extends Specification {

    private final Customer customer = new Customer()

    def "two customers created are not equal"() {
        expect:
        assertThat(customer).isNotEqualTo(anotherCustomer)

        where:
        anotherCustomer = new Customer()
    }

}

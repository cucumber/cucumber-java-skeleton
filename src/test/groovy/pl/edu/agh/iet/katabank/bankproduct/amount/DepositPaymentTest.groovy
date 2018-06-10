package pl.edu.agh.iet.katabank.bankproduct.amount

import spock.lang.Specification

import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat

class DepositPaymentTest extends Specification {

    private Payment depositPayment
    private BigDecimal amount
    private LocalDate createDate


    def "created payment has correct money amount"() {
        when:
        depositPayment = new DepositPayment(amount, createDate)

        then:
        assertThat(depositPayment.getPaymentAmount()).isEqualByComparingTo(amount)

        where:
        amount = 100.0
        createDate = LocalDate.now()
    }

    def "created payment has correct payment date"() {
        when:
        depositPayment = new DepositPayment(amount, createDate)

        then:
        assertThat(depositPayment.getPaymentDate()).isEqualTo(createDate)

        where:
        amount = 100.0
        createDate = LocalDate.now()
    }

    def "created payment is equal to itself"() {
        when:
        depositPayment = new DepositPayment(amount, createDate)

        then:
        assertThat(depositPayment).isEqualTo(depositPayment)

        where:
        amount = 100.0
        createDate = LocalDate.now()
    }

    def "created payments are not equal"() {
        when:
        depositPayment = new DepositPayment(amount, createDate)
        def secondDepositPayment = new DepositPayment(amount, createDate)

        then:
        assertThat(depositPayment).isNotEqualTo(secondDepositPayment)

        where:
        amount = 100.0
        createDate = LocalDate.now()
    }
}

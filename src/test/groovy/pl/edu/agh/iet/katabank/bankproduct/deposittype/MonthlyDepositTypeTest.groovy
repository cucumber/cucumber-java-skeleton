package pl.edu.agh.iet.katabank.bankproduct.deposittype

import spock.lang.Specification

import java.math.RoundingMode
import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat

class MonthlyDepositTypeTest extends Specification {

    private static
    final String ERROR_MESSAGE_DEPOSIT_DURATION = 'Duration for deposit type must be positive. Incorrect value: '

    private DepositType depositType

    def "close date is calculated correct"() {
        when:
        def duration = 7
        def interestRate = 7.5
        def date = LocalDate.now()
        depositType = new MonthlyDepositType(duration, interestRate)

        then:
        assertThat(depositType.calculateCloseDate(date)).isEqualTo(date.plusMonths(7))
    }

    def "interest is calculated correct"() {
        when:
        def duration = 5
        def interestRate = 10.0
        def initialAmount = 100.0
        depositType = new MonthlyDepositType(duration, interestRate)

        then:
        assertThat(depositType.calculateInterest(initialAmount))
                .isEqualByComparingTo((initialAmount * (interestRate / 100.0) * (duration / 12.0))
                .setScale(2, RoundingMode.HALF_DOWN))
    }

    def "cannot create deposit type with negative duration value"() {
        when:
        def duration = -1
        def interestRate = 10.0
        depositType = new MonthlyDepositType(duration, interestRate)

        then:
        RuntimeException ex = thrown()
        ex.message == ERROR_MESSAGE_DEPOSIT_DURATION + duration
    }

    def "cannot create deposit type with zero duration value"() {
        when:
        def duration = 0
        def interestRate = 10.0
        depositType = new MonthlyDepositType(duration, interestRate)

        then:
        RuntimeException ex = thrown()
        ex.message == ERROR_MESSAGE_DEPOSIT_DURATION + duration
    }

}

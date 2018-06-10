package pl.edu.agh.iet.katabank.bankproduct.interestpolicy

import spock.lang.Specification

import java.math.RoundingMode
import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat

class MonthlyInterestPolicyTest extends Specification {

    private InterestPolicy monthyInterestPolicy

    def "interest is zero when close date has not passed"() {
        given:
        def duration = 7
        def interestRate = 7.5
        def openDate = LocalDate.now()
        def closeDate = openDate.plusMonths(duration)
        def calculationDate = closeDate.minusDays(1)
        monthyInterestPolicy = new MonthlyInterestPolicy(interestRate)

        when:
        def interestAmount = monthyInterestPolicy.calculateInterest(10.0, openDate, calculationDate, closeDate)

        then:
        assertThat(interestAmount).isEqualByComparingTo(0.0)
    }

    def "interest is calculated correct when close date has passed"() {
        given:
        def duration = 5
        def interestRate = 10.0
        def initialAmount = 100.0
        def openDate = LocalDate.now()
        def closeDate = openDate.plusMonths(duration)
        def calculationDate = closeDate
        monthyInterestPolicy = new MonthlyInterestPolicy(interestRate)

        when:
        def interestAmount = monthyInterestPolicy.calculateInterest(initialAmount, openDate, calculationDate, closeDate)

        then:
        assertThat(interestAmount)
                .isEqualByComparingTo((initialAmount * (interestRate / 100.0) * (duration / 12.0))
                .setScale(2, RoundingMode.HALF_DOWN))
    }

}

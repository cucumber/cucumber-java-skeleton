package pl.edu.agh.iet.katabank.bankproduct.interestpolicy

import spock.lang.Specification

import java.math.RoundingMode
import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat

class DailyInterestPolicyWithInsuranceTest extends Specification {

    private InterestPolicy dailyInterestPolicyWithInsurance

    def "interest is calculated correct when close date equals calculation date"() {
        given:
        def duration = 60
        def interestRate = 10.0
        def insuranceCostPercent = 1.0
        def initialAmount = 100.0
        def openDate = LocalDate.now()
        def closeDate = openDate.plusDays(duration.toInteger())
        def calculationDate = closeDate
        dailyInterestPolicyWithInsurance = new DailyInterestPolicyWithInsurance(interestRate, insuranceCostPercent)

        when:
        def interestAmount = dailyInterestPolicyWithInsurance.calculateInterest(initialAmount, openDate, calculationDate, closeDate)

        then:
        assertThat(interestAmount)
                .isEqualByComparingTo((initialAmount * (interestRate / 100.0) * (duration / 365))
                .setScale(2, RoundingMode.HALF_DOWN))
    }

    def "interest is calculated correct when close date has not yet passed"() {
        given:
        def duration = 60.0
        def interestRate = 10.0
        def insuranceCostPercent = 5.0
        def initialAmount = 100.0
        def openDate = LocalDate.now()
        def closeDate = openDate.plusDays(duration.toInteger())
        def calculationDate = closeDate.minusDays(1)
        dailyInterestPolicyWithInsurance = new DailyInterestPolicyWithInsurance(interestRate, insuranceCostPercent)

        when:
        def interestAmount = dailyInterestPolicyWithInsurance.calculateInterest(initialAmount, openDate, calculationDate, closeDate)

        then:
        assertThat(interestAmount)
                .isEqualByComparingTo((initialAmount * (interestRate / 100.0) * ((duration-1) / 365))
                .setScale(2, RoundingMode.HALF_DOWN))
    }

    def "interest is calculated correct when calculation date is after close date"() {
        given:
        def duration = 60.0
        def interestRate = 10.0
        def insuranceCostPercent = 5.0
        def initialAmount = 100.0
        def openDate = LocalDate.now()
        def closeDate = openDate.plusDays(duration.toInteger())
        def calculationDate = closeDate.plusDays(1)
        dailyInterestPolicyWithInsurance = new DailyInterestPolicyWithInsurance(interestRate, insuranceCostPercent)

        when:
        def interestAmount = dailyInterestPolicyWithInsurance.calculateInterest(initialAmount, openDate, calculationDate, closeDate)

        then:
        assertThat(interestAmount)
                .isEqualByComparingTo((initialAmount * (interestRate / 100.0) * ((duration) / 365))
                .setScale(2, RoundingMode.HALF_DOWN))
    }
}

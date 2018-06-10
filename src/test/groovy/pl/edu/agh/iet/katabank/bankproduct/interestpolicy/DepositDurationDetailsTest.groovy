package pl.edu.agh.iet.katabank.bankproduct.interestpolicy

import spock.lang.Specification

import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat
import static pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails.DurationType.*

class DepositDurationDetailsTest extends Specification {

    private DepositDurationDetails depositDuration
    private int duration
    private LocalDate openDate
    private LocalDate closeDate

    def "yearly deposit calculates close date correctly"() {
        given:
        depositDuration = new DepositDurationDetails(duration, YEARS)

        when:
        closeDate = depositDuration.calculateCloseDate(openDate)

        then:
        assertThat(closeDate).isEqualTo(openDate.plusYears(duration))

        where:
        duration = 3
        openDate = LocalDate.now()
    }

    def "monthly deposit calculates close date correctly"() {
        given:
        depositDuration = new DepositDurationDetails(duration, MONTHS)

        when:
        closeDate = depositDuration.calculateCloseDate(openDate)

        then:
        assertThat(closeDate).isEqualTo(openDate.plusMonths(duration))

        where:
        duration = 18
        openDate = LocalDate.now()
    }

    def "daily deposit calculates close date correctly"() {
        given:
        depositDuration = new DepositDurationDetails(duration, DAYS)

        when:
        closeDate = depositDuration.calculateCloseDate(openDate)

        then:
        assertThat(closeDate).isEqualTo(openDate.plusDays(duration))

        where:
        duration = 180
        openDate = LocalDate.now()
    }
}

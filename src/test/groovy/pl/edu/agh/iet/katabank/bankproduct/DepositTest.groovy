package pl.edu.agh.iet.katabank.bankproduct

import pl.edu.agh.iet.katabank.Customer
import pl.edu.agh.iet.katabank.bankproduct.amount.DepositPayment
import pl.edu.agh.iet.katabank.bankproduct.amount.Payment
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.InterestPolicy
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.MonthlyInterestPolicy
import spock.lang.Specification

import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat
import static pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails.DurationType.MONTHS

class DepositTest extends Specification {

    private static final String ERROR_MESSAGE_CLOSE_DEPOSIT_ALREADY_CLOSED = 'Cannot close already closed deposit'
    private static final String CANNOT_ADD_PAYMENT_TO_CLOSED_DEPOSIT = "Cannot add payment to closed deposit"
    private static final String PAYMENT_DATE_BEFORE_DEPOSIT_OPEN_DATE = "Payment's date is before deposit open date."

    private final Customer customer = new Customer()
    private final Account account = new Account(customer)
    private int durationInMonths = 12
    private BigDecimal amount = 100.0
    private InterestPolicy interestPolicy = new MonthlyInterestPolicy(10.0)
    private Payment depositPayment = new DepositPayment(amount, LocalDate.now())
    private final DepositDurationDetails depositDurationDetails = new DepositDurationDetails(durationInMonths, MONTHS)
    private Deposit deposit


    def "two deposits created for the same account are not equal"() {
        when:
        def firstDeposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        def secondDeposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        then:
        assertThat(firstDeposit).isNotEqualTo(secondDeposit)
    }

    def "deposit created has the same owner as connected account"() {
        when:
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        then:
        assertThat(deposit.getOwner()).isEqualTo(account.getOwner())
    }

    def "deposit open date equals first payment date"() {
        given:
        def secondDepositPayment = new DepositPayment(25.0, LocalDate.now().plusMonths(1))
        def secondInterestPolicy = new MonthlyInterestPolicy(12.0)

        when:
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        deposit.addPayment(secondDepositPayment, secondInterestPolicy)

        then:
        assertThat(deposit.getOpenDate()).isEqualTo(depositPayment.getPaymentDate())
        assertThat(deposit.getOpenDate()).isNotEqualTo(secondDepositPayment.getPaymentDate())
    }

    def "when deposit closed balance is zero"() {
        given:
        def closeDate = depositPayment.getPaymentDate().plusMonths(durationInMonths)
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        when:
        deposit.closeDeposit(closeDate)

        then:
        assertThat(deposit.getBalance()).isZero()
    }

    def "when deposit created it is open"() {
        when:
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        then:
        assertThat(deposit.isOpen()).isTrue()
    }

    def "deposit already closed can't be closed"() {
        given:
        def closeDate = depositPayment.getPaymentDate().plusMonths(durationInMonths)
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        when:
        deposit.closeDeposit(closeDate)
        deposit.closeDeposit(closeDate)

        then:
        assertThat(deposit.isOpen()).isFalse()
        RuntimeException ex = thrown()
        ex.message == ERROR_MESSAGE_CLOSE_DEPOSIT_ALREADY_CLOSED
    }

    def "when deposit created it returns correct close date"() {
        given:
        def closeDate = depositPayment.getPaymentDate().plusMonths(durationInMonths)

        when:
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        then:
        assertThat(deposit.getCloseDate()).isEqualTo(closeDate)
    }

    def "when more than one payment deposit returns correct balance"() {
        given:
        def secondDepositPayment = new DepositPayment(25.0, LocalDate.now().plusMonths(1))
        def secondInterestPolicy = new MonthlyInterestPolicy(12.0)

        when:
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        deposit.addPayment(secondDepositPayment, secondInterestPolicy)

        then:
        assertThat(deposit.getBalance())
                .isEqualByComparingTo(depositPayment.getPaymentAmount() + secondDepositPayment.getPaymentAmount())
    }

    def "when more than one payment deposit returns correct list of payments"() {
        given:
        def secondDepositPayment = new DepositPayment(25.0, LocalDate.now().plusMonths(1))
        def secondInterestPolicy = new MonthlyInterestPolicy(12.0)

        when:
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        deposit.addPayment(secondDepositPayment, secondInterestPolicy)

        then:
        assertThat(deposit.getInterestRates())
                .containsExactly(interestPolicy.getYearlyInterestRatePercent(),
                secondInterestPolicy.getYearlyInterestRatePercent())
    }

    def "cannot add payment to closed deposit"() {
        given:
        def secondDepositPayment = new DepositPayment(25.0, LocalDate.now().plusMonths(1))
        def secondInterestPolicy = new MonthlyInterestPolicy(12.0)
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        when:
        deposit.closeDeposit(LocalDate.now())
        deposit.addPayment(secondDepositPayment, secondInterestPolicy)

        then:
        assertThat(deposit.isOpen()).isFalse()
        RuntimeException ex = thrown()
        ex.message == CANNOT_ADD_PAYMENT_TO_CLOSED_DEPOSIT
    }

    def "cannot add payment with date before open deposit date"() {
        given:
        def secondDepositPayment = new DepositPayment(amount, LocalDate.now().minusDays(1))
        def secondInterestPolicy = new MonthlyInterestPolicy(12.0)
        deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)

        when:
        deposit.addPayment(secondDepositPayment, secondInterestPolicy)

        then:
        RuntimeException ex = thrown()
        ex.message == PAYMENT_DATE_BEFORE_DEPOSIT_OPEN_DATE
    }

}

package pl.edu.agh.iet.katabank.repository

import pl.edu.agh.iet.katabank.Customer
import pl.edu.agh.iet.katabank.bankproduct.Account
import pl.edu.agh.iet.katabank.bankproduct.Deposit
import pl.edu.agh.iet.katabank.bankproduct.amount.DepositPayment
import pl.edu.agh.iet.katabank.bankproduct.amount.Payment
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.InterestPolicy
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.MonthlyInterestPolicy
import spock.lang.Specification

import java.time.LocalDate

import static org.assertj.core.api.Assertions.assertThat
import static pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails.DurationType.MONTHS

class InMemoryBankProductsRepositoryTest extends Specification {

    private final InMemoryBankProductsRepository bankProductsRepository = new InMemoryBankProductsRepository()
    private final InterestPolicy interestPolicy = new MonthlyInterestPolicy(10.0)
    private final DepositDurationDetails depositDurationDetails = new DepositDurationDetails(12, MONTHS)
    private final Payment depositPayment = new DepositPayment(10.0, LocalDate.now())

    def "when customer has no accounts, empty set is returned"() {
        expect:
        assertThat(bankProductsRepository.findAccountsForCustomer(customer)).isEmpty()

        where:
        customer = new Customer()
    }

    def "when customer has no deposits, empty set is returned"() {
        expect:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).isEmpty()

        where:
        customer = new Customer()
    }

    def "when customer has a deposit, the set contains the deposit"() {
        given:
        def customer = new Customer()
        def account = new Account(customer)
        account.setBalance(20.0)

        when:
        def deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        bankProductsRepository.addAccount(account)
        bankProductsRepository.addDeposit(deposit)

        then:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).contains(deposit)
    }

    def "when customer has multiple deposits to multiple accounts, the set contains all the deposit"() {
        given:
        def customer = new Customer()
        def account = new Account(customer)
        account.setBalance(20.0)
        def anotherAccount = new Account(customer)
        anotherAccount.setBalance(20.0)

        when:
        def deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        def anotherDeposit = new Deposit(anotherAccount, depositPayment, depositDurationDetails, interestPolicy)
        def yetAnotherDeposit = new Deposit(anotherAccount, depositPayment, depositDurationDetails, interestPolicy)
        bankProductsRepository.addAccount(account)
        bankProductsRepository.addAccount(anotherAccount)
        bankProductsRepository.addDeposit(deposit)
        bankProductsRepository.addDeposit(anotherDeposit)
        bankProductsRepository.addDeposit(yetAnotherDeposit)

        then:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).contains(deposit, anotherDeposit, yetAnotherDeposit)
    }

    def "when multiple customers have deposits, the set contains deposits for one customer only"() {
        given:
        def customer = new Customer()
        def anotherCustomer = new Customer()
        def account = new Account(customer)
        def anotherAccount = new Account(anotherCustomer)
        account.setBalance(20.0)
        anotherAccount.setBalance(20.0)

        when:
        def deposit = new Deposit(account, depositPayment, depositDurationDetails, interestPolicy)
        def anotherDeposit = new Deposit(anotherAccount, depositPayment, depositDurationDetails, interestPolicy)
        bankProductsRepository.addAccount(account)
        bankProductsRepository.addAccount(anotherAccount)
        bankProductsRepository.addDeposit(deposit)
        bankProductsRepository.addDeposit(anotherDeposit)

        then:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).containsExactly(deposit)
    }
}

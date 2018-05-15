package pl.edu.agh.iet.katabank

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class InMemoryBankProductsRepositoryTest extends Specification{

    private final InMemoryBankProductsRepository bankProductsRepository = new InMemoryBankProductsRepository()

    def "when customer has no accounts, empty set is returned" (){
        expect:
        assertThat(bankProductsRepository.findAccountsForCustomer(customer)).isEmpty()

        where:
        customer = new Customer()
    }

    def "when customer has no deposits, empty set is returned" (){
        expect:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).isEmpty()

        where:
        customer = new Customer()
    }

    def "when customer has a deposit, the set contains the deposit" (){
        when:
        def customer = new Customer()
        def account = new Account(customer)
        account.setBalance(20)
        def deposit = new Deposit(account, 10)
        bankProductsRepository.addAccount(account)
        bankProductsRepository.addDeposit(deposit)

        then:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).contains(deposit)
    }

    def "when customer has multiple deposits to multiple accounts, the set contains all the deposit" (){
        when:
        def customer = new Customer()
        def account = new Account(customer)
        account.setBalance(20)
        def anotherAccount = new Account(customer)
        anotherAccount.setBalance(20)
        def deposit = new Deposit(account, 10)
        def anotherDeposit = new Deposit(anotherAccount, 10)
        def yetAnotherDeposit = new Deposit (anotherAccount, 10)
        bankProductsRepository.addAccount(account)
        bankProductsRepository.addAccount(anotherAccount)
        bankProductsRepository.addDeposit(deposit)
        bankProductsRepository.addDeposit(anotherDeposit)
        bankProductsRepository.addDeposit(yetAnotherDeposit)

        then:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).contains(deposit, anotherDeposit, yetAnotherDeposit)
    }

    def "when multiple customers have deposits, the set contains deposits for one customer only" (){
        when:
        def customer = new Customer()
        def anotherCustomer = new Customer()
        def account = new Account(customer)
        def anotherAccount = new Account(anotherCustomer)
        account.setBalance(20)
        anotherAccount.setBalance(20)
        def deposit = new Deposit(account, 10)
        def anotherDeposit = new Deposit(anotherAccount, 10)
        bankProductsRepository.addAccount(account)
        bankProductsRepository.addAccount(anotherAccount)
        bankProductsRepository.addDeposit(deposit)
        bankProductsRepository.addDeposit(anotherDeposit)

        then:
        assertThat(bankProductsRepository.findDepositsForCustomer(customer)).containsExactly(deposit)
    }
}

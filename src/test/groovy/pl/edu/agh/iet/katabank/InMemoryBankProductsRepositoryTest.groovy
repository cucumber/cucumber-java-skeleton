package pl.edu.agh.iet.katabank

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class InMemoryBankProductsRepositoryTest extends Specification{

    private final InMemoryBankProductsRepository accountsRepository = new InMemoryBankProductsRepository()

    def "when customer has no accounts, empty set is returned" (){
        expect:
        assertThat(accountsRepository.findAccountsForCustomer(customer)).isEmpty()

        where:
        customer = new Customer()
    }

    def "when customer has no deposits, empty set is returned" (){
        expect:
        assertThat(accountsRepository.findDepositsForCustomer(customer)).isEmpty()

        where:
        customer = new Customer()
    }

    def "when has a deposit, the set contains the deposit" (){


        when:
        def customer = new Customer()
        def account = new Account(customer)
        account.setBalance(20)
        def deposit = new Deposit(account, 10)
        accountsRepository.addAccount(account)
        accountsRepository.addDeposit(deposit)

        then:
        assertThat(accountsRepository.findDepositsForCustomer(customer)).contains(deposit)
    }
}

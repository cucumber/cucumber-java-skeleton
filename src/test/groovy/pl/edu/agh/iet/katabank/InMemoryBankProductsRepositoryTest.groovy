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
}

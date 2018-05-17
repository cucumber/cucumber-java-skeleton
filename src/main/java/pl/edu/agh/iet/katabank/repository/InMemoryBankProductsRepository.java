package pl.edu.agh.iet.katabank.repository;

import pl.edu.agh.iet.katabank.Customer;
import pl.edu.agh.iet.katabank.bankproduct.Account;
import pl.edu.agh.iet.katabank.bankproduct.Deposit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryBankProductsRepository implements BankProductsRepository {

    private Map<Customer, Set<Account>> accountsMap;
    private Map<Account, Set<Deposit>> depositsMap;

    public InMemoryBankProductsRepository() {
        this.accountsMap = new HashMap<>();
        this.depositsMap = new HashMap<>();
    }

    @Override
    public void addAccount (Account account){
        Set<Account> customerAccounts = accountsMap.get(account.getOwner()) != null
                ? accountsMap.get(account.getOwner()) : new HashSet<>();
        customerAccounts.add(account);
        accountsMap.put(account.getOwner(), customerAccounts);
    }

    @Override
    public Set<Account> findAccountsForCustomer(Customer customer) {
        return accountsMap.getOrDefault(customer, new HashSet<>());
    }

    @Override
    public void addDeposit(Deposit deposit) {
        Set<Deposit> accountsDeposits = depositsMap.get(deposit.getConnectedAccount()) != null
                ? depositsMap.get(deposit.getConnectedAccount()) : new HashSet<>();
        accountsDeposits.add(deposit);
        depositsMap.put(deposit.getConnectedAccount(), accountsDeposits);
    }

    @Override
    public Set<Deposit> findDepositsForCustomer(Customer customer) {
        Set<Deposit> customersDeposits = new HashSet<>();
        for (Account account : findAccountsForCustomer(customer)){
            customersDeposits.addAll(depositsMap.getOrDefault(account, new HashSet<>()));
        }
        return customersDeposits;
    }


}

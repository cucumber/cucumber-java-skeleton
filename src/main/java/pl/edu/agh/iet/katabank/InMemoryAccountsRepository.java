package pl.edu.agh.iet.katabank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryAccountsRepository implements AccountsRepository {

    private Map<Customer, Set<Account>> accountsMap;

    public InMemoryAccountsRepository() {
        this.accountsMap = new HashMap<>();
    }

    @Override
    public void addAccount(Account account) {
        Set<Account> customerAccounts = accountsMap.get(account.getOwner()) != null
                ? accountsMap.get(account.getOwner()) : new HashSet<>();
        customerAccounts.add(account);
        accountsMap.put(account.getOwner(), customerAccounts);
    }

    @Override
    public Set<Account> findAccountsForCustomer(Customer customer) {
        return accountsMap.getOrDefault(customer, new HashSet<>());
    }
}

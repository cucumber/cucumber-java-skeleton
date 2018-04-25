package pl.edu.agh.iet.katabank;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Account {

    private final Customer owner;
    private BigDecimal balance;
    private final UUID id;

    public Account(Customer owner) {
        this.owner = owner;
        this.id = UUID.randomUUID();
        this.balance =new BigDecimal(0);
    }

    public Customer getOwner() {
        return this.owner;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

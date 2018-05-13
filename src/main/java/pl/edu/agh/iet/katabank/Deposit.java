package pl.edu.agh.iet.katabank;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Deposit implements BankProduct {

    private BigDecimal balance;
    private Account connectedAccount;
    private final UUID id;

    public Deposit (Account connectedAccount, BigDecimal blance) {
        this.balance = balance;
        this.connectedAccount = connectedAccount;
        this.id = UUID.randomUUID();
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public Customer getOwner() {
        return connectedAccount.getOwner();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return Objects.equals(id, deposit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

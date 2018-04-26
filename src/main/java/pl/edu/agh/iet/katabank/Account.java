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
        this.balance = new BigDecimal(0);
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

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void withdraw(BigDecimal amount) throws IllegalArgumentException {
        checkValidAmount(amount);
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("The amount to withdraw is greater than account balance.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        checkValidAmount(amount);
        this.balance = this.balance.add(amount);
    }

    private void checkValidAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Incorrect amount to process: "
                    + (amount == null ? null : amount.toString()));
        }
    }

    public void transfer(Account targetAccount, BigDecimal bigDecimal) {
        if (targetAccount == null) {
            throw new RuntimeException("Target account cannot be null.");
        }
        this.withdraw(bigDecimal);
        targetAccount.deposit(bigDecimal);
    }
}

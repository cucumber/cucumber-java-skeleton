package pl.edu.agh.iet.katabank;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Deposit implements BankProduct {

    private BigDecimal balance;
    private Account connectedAccount;
    private final UUID id;
    private final String INCORRECT_AMOUNT_MESSAGE = "Incorrect initial balance to open deposit: ";

    public Deposit (Account account, BigDecimal initialBalance) {
        try {
            account.withdraw(initialBalance);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INCORRECT_AMOUNT_MESSAGE + initialBalance);
        }

        this.balance = initialBalance;
        this.connectedAccount = account;
        this.id = UUID.randomUUID();
    }

    public Account getConnectedAccount() {
        return this.connectedAccount;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public Customer getOwner() {
        return this.connectedAccount.getOwner();
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

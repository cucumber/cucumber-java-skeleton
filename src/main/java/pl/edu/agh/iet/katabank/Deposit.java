package pl.edu.agh.iet.katabank;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Deposit implements BankProduct {

    private BigDecimal balance;
    private Account connectedAccount;
    private final UUID id;
    private final String INCORRECT_AMOUNT_MESSAGE = "Incorrect amount to process: ";

    public Deposit (Account account, BigDecimal newDepositBalance) {
        checkValidAmount(account, newDepositBalance);
        account.withdraw(newDepositBalance);
        this.balance = newDepositBalance;
        this.connectedAccount = account;
        this.id = UUID.randomUUID();
    }

    private void checkValidAmount(Account connectedAccount, BigDecimal newDepositBalance) {
        if (newDepositBalance==null || newDepositBalance.signum()<=0 || newDepositBalance.compareTo(connectedAccount.getBalance())>0){
            throw new IllegalArgumentException(INCORRECT_AMOUNT_MESSAGE + newDepositBalance);
        }
    }

    public Account getConnectedAccount() {
        return connectedAccount;
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

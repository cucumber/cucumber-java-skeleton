package pl.edu.agh.iet.katabank.bankproduct;

import pl.edu.agh.iet.katabank.Customer;
import pl.edu.agh.iet.katabank.bankproduct.amount.Payment;
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.DepositDurationDetails;
import pl.edu.agh.iet.katabank.bankproduct.interestpolicy.InterestPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Deposit implements BankProduct {

    private static final String CANNOT_CLOSE_ALREADY_CLOSED_DEPOSIT_MESSAGE = "Cannot close already closed deposit";
    private static final String ERROR_WHILE_COUNTING_BALANCE_ON_CLOSE = "Error while counting balance on close.";
    private static final String CANNOT_ADD_PAYMENT_TO_CLOSED_DEPOSIT = "Cannot add payment to closed deposit";
    private static final String PAYMENT_DATE_BEFORE_DEPOSIT_OPEN_DATE = "Payment's date is before deposit open date.";

    private Map<Payment, InterestPolicy> payments;
    private Account connectedAccount;
    private final UUID id;
    private final LocalDate openDate;
    private final DepositDurationDetails durationDetails;
    private boolean open;

    public Deposit(Account connectedAccount, Payment initialPayment, DepositDurationDetails durationDetails, InterestPolicy interestPolicy) {
        this.id = UUID.randomUUID();
        this.payments = new LinkedHashMap<>();
        this.payments.put(initialPayment, interestPolicy);
        this.connectedAccount = connectedAccount;
        this.openDate = initialPayment.getPaymentDate();
        this.durationDetails = durationDetails;
        this.open = true;
    }

    public Account getConnectedAccount() {
        return this.connectedAccount;
    }

    public BigDecimal getBalance() {
        if (!isOpen()) {
            return BigDecimal.ZERO;
        }
        return payments.keySet().stream().map(Payment::getPaymentAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public LocalDate getOpenDate() {
        return this.openDate;
    }

    public boolean isOpen() {
        return this.open;
    }

    public LocalDate getCloseDate() {
        return this.durationDetails.calculateCloseDate(this.openDate);
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

    public void closeDeposit(final LocalDate date) {
        if (!isOpen()) {
            throw new RuntimeException(CANNOT_CLOSE_ALREADY_CLOSED_DEPOSIT_MESSAGE);
        }
        BigDecimal closeBalanceWithInterest = calculateWholeBalanceOnClose(date);
        depositCloseBalanceWithInterestToConnectedAccount(closeBalanceWithInterest);
        this.open = false;
    }

    private void depositCloseBalanceWithInterestToConnectedAccount(BigDecimal closeBalanceWithInterest) {
        this.connectedAccount.deposit(closeBalanceWithInterest);
    }

    private BigDecimal calculateWholeBalanceOnClose(final LocalDate date) {
        return payments.entrySet().stream().map(paymentEntry ->
                calculateBalanceWithInterest(paymentEntry.getKey().getPaymentAmount(), paymentEntry.getValue()
                        .calculateInterest(paymentEntry.getKey().getPaymentAmount(),
                                paymentEntry.getKey().getPaymentDate(), date, this.getCloseDate())))
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException(ERROR_WHILE_COUNTING_BALANCE_ON_CLOSE));
    }

    private BigDecimal calculateBalanceWithInterest(BigDecimal startBalance, BigDecimal interestBalance) {
        return startBalance.add(interestBalance);
    }

    public void addPayment(Payment payment, InterestPolicy interestPolicy) {
        if (!isOpen()) throw new RuntimeException(CANNOT_ADD_PAYMENT_TO_CLOSED_DEPOSIT);
        if (payment.getPaymentDate().isBefore(this.openDate))
            throw new RuntimeException(PAYMENT_DATE_BEFORE_DEPOSIT_OPEN_DATE);
        payments.put(payment, interestPolicy);
    }

    public List<BigDecimal> getInterestRates() {
        List<BigDecimal> interestRates = new LinkedList<>();
        for (InterestPolicy policy : payments.values()) {
            interestRates.add(policy.getYearlyInterestRatePercent());
        }
        return interestRates;
    }
}

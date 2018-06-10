package pl.edu.agh.iet.katabank.bankproduct.amount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DepositPayment implements Payment {

    private final UUID id;
    private final BigDecimal paymentAmount;
    private final LocalDate paymentDate;

    public DepositPayment(BigDecimal paymentAmount, LocalDate paymentDate) {
        this.id = UUID.randomUUID();
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }

    @Override
    public BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    @Override
    public LocalDate getPaymentDate() {
        return this.paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositPayment that = (DepositPayment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

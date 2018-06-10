package pl.edu.agh.iet.katabank.bankproduct.amount;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Payment {

    BigDecimal getPaymentAmount();

    LocalDate getPaymentDate();

}

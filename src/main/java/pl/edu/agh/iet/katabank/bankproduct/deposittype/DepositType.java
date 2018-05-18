package pl.edu.agh.iet.katabank.bankproduct.deposittype;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DepositType {

    BigDecimal getYearlyInterestRatePercent();

    LocalDate calculateCloseDate(LocalDate openDate);

    BigDecimal calculateInterest(BigDecimal amount);
}

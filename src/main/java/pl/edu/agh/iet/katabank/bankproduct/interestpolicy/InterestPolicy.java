package pl.edu.agh.iet.katabank.bankproduct.interestpolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.math.RoundingMode.HALF_DOWN;

public interface InterestPolicy {

    BigDecimal ONE_HUNDRED_PERCENT = new BigDecimal(100);
    BigDecimal TWELVE_MONTHS = new BigDecimal(12);
    int MONEY_SCALE = 2;
    int CALCULATION_SCALE = 10;
    RoundingMode ROUNDING_MODE = HALF_DOWN;

    BigDecimal getYearlyInterestRatePercent();

    BigDecimal calculateInterest(BigDecimal amount, LocalDate openDate, LocalDate calculationDate, LocalDate plannedTerminationDate);
}

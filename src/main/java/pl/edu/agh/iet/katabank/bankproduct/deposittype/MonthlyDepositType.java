package pl.edu.agh.iet.katabank.bankproduct.deposittype;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.math.RoundingMode.HALF_DOWN;

public class MonthlyDepositType implements DepositType {

    private static final String INCORRECT_DURATION_MESSAGE = "Duration for deposit type must be positive. Incorrect value: ";
    private static final BigDecimal ONE_HUNDRED_PERCENT = new BigDecimal(100);
    private static final BigDecimal TWELVE_MONTHS = new BigDecimal(12);
    private static final int MONEY_SCALE = 2;
    private static final int CALCULATION_SCALE = 10;
    private static final RoundingMode ROUNDING_MODE = HALF_DOWN;

    private int duration;
    private BigDecimal yearlyInterestRatePercent;

    public MonthlyDepositType(int durationInMonths, BigDecimal yearlyInterestRatePercent) {
        if (durationInMonths <= 0) {
            throw new RuntimeException(INCORRECT_DURATION_MESSAGE + durationInMonths);
        }
        this.duration = durationInMonths;
        this.yearlyInterestRatePercent = yearlyInterestRatePercent;
    }

    @Override
    public BigDecimal getYearlyInterestRatePercent() {
        return yearlyInterestRatePercent;
    }

    @Override
    public LocalDate calculateCloseDate(LocalDate openDate) {
        return openDate.plusMonths(this.duration);
    }

    @Override
    public BigDecimal calculateInterest(BigDecimal amount) {
        return (amount.multiply(calculateInterestRateMultiplier())).setScale(MONEY_SCALE, ROUNDING_MODE);
    }

    private BigDecimal calculateInterestRateMultiplier() {
        return yearlyInterestRatePercent.divide(ONE_HUNDRED_PERCENT, CALCULATION_SCALE, ROUNDING_MODE)
                .multiply(new BigDecimal(duration).divide(TWELVE_MONTHS, CALCULATION_SCALE, ROUNDING_MODE));
    }
}

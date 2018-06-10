package pl.edu.agh.iet.katabank.bankproduct.interestpolicy;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;

public class MonthlyInterestPolicy implements InterestPolicy {

    private BigDecimal yearlyInterestRatePercent;

    public MonthlyInterestPolicy(BigDecimal yearlyInterestRatePercent) {
        this.yearlyInterestRatePercent = yearlyInterestRatePercent;
    }

    @Override
    public BigDecimal getYearlyInterestRatePercent() {
        return this.yearlyInterestRatePercent;
    }

    @Override
    public BigDecimal calculateInterest(BigDecimal amount, LocalDate openDate, LocalDate calculationDate, LocalDate plannedTerminationDate) {
        if (calculationDate.isBefore(plannedTerminationDate)) {
            return ZERO;
        }
        int depositDuration = getDuration(openDate, plannedTerminationDate);
        return (amount.multiply(calculateInterestRateMultiplier(depositDuration))).setScale(MONEY_SCALE, ROUNDING_MODE);
    }

    private BigDecimal calculateInterestRateMultiplier(int duration) {
        return yearlyInterestRatePercent.divide(ONE_HUNDRED_PERCENT, CALCULATION_SCALE, ROUNDING_MODE)
                .multiply(new BigDecimal(duration).divide(TWELVE_MONTHS, CALCULATION_SCALE, ROUNDING_MODE));
    }

    private int getDuration(LocalDate openDate, LocalDate closeDate) {
        return (closeDate.getYear() - openDate.getYear()) * TWELVE_MONTHS.intValue()
                + (closeDate.getMonthValue() - openDate.getMonthValue());
    }
}

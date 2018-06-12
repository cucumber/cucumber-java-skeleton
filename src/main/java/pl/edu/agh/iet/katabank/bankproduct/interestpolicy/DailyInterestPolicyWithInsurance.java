package pl.edu.agh.iet.katabank.bankproduct.interestpolicy;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

public class DailyInterestPolicyWithInsurance implements InterestPolicy {

    private BigDecimal yearlyInterestRatePercent;
    private BigDecimal insuranceCostPercent;

    public DailyInterestPolicyWithInsurance(BigDecimal yearlyInterestRatePercent, BigDecimal insuranceCostPercent) {
        this.yearlyInterestRatePercent = yearlyInterestRatePercent;
        this.insuranceCostPercent = insuranceCostPercent;
    }

    @Override
    public BigDecimal getYearlyInterestRatePercent() {
        return this.yearlyInterestRatePercent;
    }

    @Override
    public BigDecimal calculateInterest(BigDecimal amount, LocalDate openDate, LocalDate calculationDate, LocalDate plannedTerminationDate) {
        int depositDuration = getDuration(openDate, actualClosingDate(plannedTerminationDate, calculationDate));
        return (amount.multiply(calculateInterestRateMultiplier(depositDuration))).setScale(MONEY_SCALE, ROUNDING_MODE);
    }

    private BigDecimal calculateInterestRateMultiplier(int depositDuration) {
        return yearlyInterestRatePercent.divide(ONE_HUNDRED_PERCENT, CALCULATION_SCALE, ROUNDING_MODE)
                .multiply(new BigDecimal(depositDuration)).divide(DAYS_IN_YEAR, CALCULATION_SCALE, ROUNDING_MODE);
    }

    private int getDuration(LocalDate openDate, LocalDate actualClosingDate) {
        return (int)((Duration.between(openDate.atStartOfDay(), actualClosingDate.atStartOfDay())).toDays());
    }

    @Override
    public BigDecimal preProcessAmount(BigDecimal amount) {
        return amount.subtract(amount.multiply(this.insuranceCostPercent)
                .divide(ONE_HUNDRED_PERCENT, CALCULATION_SCALE, ROUNDING_MODE));
    }

    private LocalDate actualClosingDate (LocalDate plannedTerminationDate, LocalDate calculationDate){
        return plannedTerminationDate.isBefore(calculationDate) ? plannedTerminationDate : calculationDate;
    }
}

package pl.edu.agh.iet.katabank.bankproduct.interestpolicy;

import java.time.LocalDate;

public class DepositDurationDetails {

    private static final String WRONG_DURATION_TYPE = "Wrong duration type: ";
    private final int duration;
    private final DurationType durationType;

    public DepositDurationDetails(int duration, DurationType durationType) {
        this.duration = duration;
        this.durationType = durationType;
    }

    public LocalDate calculateCloseDate(LocalDate openDate) {
        switch (durationType) {
            case DAYS:
                return openDate.plusDays(this.duration);
            case MONTHS:
                return openDate.plusMonths(this.duration);
            case YEARS:
                return openDate.plusYears(this.duration);
        }
        throw new RuntimeException(WRONG_DURATION_TYPE + this.durationType);
    }

    public enum DurationType {
        DAYS, MONTHS, YEARS
    }
}

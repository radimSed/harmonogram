import java.time.LocalDate;
import java.util.Date;

public class Task {
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int periodFrequency;
    private char periodDuration;

    public Task(String taskName, LocalDate startDate, LocalDate endDate, int periodFrequency, char periodDuration){
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodFrequency = periodFrequency;
        this.periodDuration = periodDuration;
    }

    public String getTaskName() {
        return taskName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPeriodFrequency() {
        return periodFrequency;
    }

    public char getPeriodDuration() {
        return periodDuration;
    }

    public boolean doesItStart(LocalDate harmonogramPeriodStartDate, LocalDate harmonogramPeriodEndDate){
        if(harmonogramPeriodStartDate.isAfter(endDate) || harmonogramPeriodEndDate.isBefore(startDate)){ //harmonogramPeriod start/end is outside period of the task --> false
            return false;
        }
        LocalDate date = startDate; //dates of the task
        do {
            if ((harmonogramPeriodStartDate.isBefore(date) || harmonogramPeriodStartDate.isEqual(date)) &&
                    (harmonogramPeriodEndDate.isAfter(date))) { //start date is in given period -->return true
                return true;
            }
            if (periodDuration == 'W') { //weekly period
                date = date.plusWeeks(periodFrequency);
            } else if (periodDuration == 'M') { //nonthly period
                date = date.plusMonths(periodFrequency);
            } else if (periodDuration == 'Y') { //yearly period
                date = date.plusYears(periodFrequency);
            } else {//dayly period
                date = date.plusDays(periodFrequency);
            }

        } while (date.isBefore(endDate) || date.isEqual(endDate));
        return false;
    }
}

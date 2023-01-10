package local.festerspears.homework;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "monthly_usage_details")
public final class MonthlyUsageDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id;
    private final long customerId;
    private final String monthOfUsage;
    private final int yearOfUsage;
    private final double usage;

    protected MonthlyUsageDetail() {
        this.id = 0;
        this.customerId = 0;
        this.monthOfUsage = "January";
        this.yearOfUsage = 2023;
        this.usage = 0.0;
    }

    public MonthlyUsageDetail(long customerId, String month, int year, double usage) {
        this.id = 0;
        this.customerId = customerId;
        this.monthOfUsage = month;
        this.yearOfUsage = year;
        this.usage = usage;
    }

    public long getId() {
        return this.id;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public String getMonthOfUsage() {
        return this.monthOfUsage;
    }

    public int getYearOfUsage() {
        return this.yearOfUsage;
    }

    public double getUsage() {
        return this.usage;
    }

    public int compareByAge(MonthlyUsageDetail thatDetail) {
        int result = 0;
        int thisMonth = MonthTranscoder.toInt(this.getMonthOfUsage());
        int thatMonth = MonthTranscoder.toInt(thatDetail.getMonthOfUsage());
        if (this.getYearOfUsage() < thatDetail.getYearOfUsage()) {
            result = 1;
        } else if (this.getYearOfUsage() > thatDetail.getYearOfUsage()) {
            result = -1;
        } else if (thisMonth < thatMonth) {
            result = 1;
        } else if (thisMonth > thatMonth) {
            result = -1;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        MonthlyUsageDetail usageDetail = (MonthlyUsageDetail) obj;
        boolean isEqual = false;
        if (this.getMonthOfUsage().equalsIgnoreCase(usageDetail.getMonthOfUsage())
                && this.yearOfUsage == usageDetail.getYearOfUsage()
                && this.getUsage() == usageDetail.getUsage()) {
            isEqual = true;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return String.format("%s%s", this.getMonthOfUsage(), this.getYearOfUsage()).hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s, \"%s\", %s, %s)", this.getCustomerId(), this.getMonthOfUsage(), this.getUsage(),
                this.getYearOfUsage());
    }
}

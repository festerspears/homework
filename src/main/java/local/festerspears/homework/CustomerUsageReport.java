package local.festerspears.homework;

import java.util.List;

public final class CustomerUsageReport {

    private final long customerId;
    private final List<MonthlyUsageDetail> monthlyUsageDetails;

    public CustomerUsageReport(long customerId, List<MonthlyUsageDetail> monthlyUsageDetails) {
        this.customerId = customerId;
        this.monthlyUsageDetails = monthlyUsageDetails;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public List<MonthlyUsageDetail> getMonthlyUsageDetails() {
        return this.monthlyUsageDetails;
    }

    public double getLastThreeMonthsRollingAvg() {
        return RollingAverageCalculator.threeMonthRollingAvg(this.monthlyUsageDetails);
    }
}

package local.festerspears.homework;

import java.util.List;

public final class CustomerUsageReport {

    private final String customerId;
    private final List<MonthlyUsageDetail> monthlyUsageDetails;

    public CustomerUsageReport(String custId, List<MonthlyUsageDetail> monthlyUsageDetails) {
        this.customerId = custId;
        this.monthlyUsageDetails = monthlyUsageDetails;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public List<MonthlyUsageDetail> getMonthlyUsageDetails() {
        return this.monthlyUsageDetails;
    }

    public double getLastThreeMonthsRollingAvg() {
        return RollingAverageCalculator.threeMonthRollingAvg(this.monthlyUsageDetails);
    }
}

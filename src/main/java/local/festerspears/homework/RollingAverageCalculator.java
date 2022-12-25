package local.festerspears.homework;

import java.util.List;

public class RollingAverageCalculator {

    public static double threeMonthRollingAvg(List<MonthlyUsageDetail> usageDetails) {
        return usageDetails.stream()
                .sorted((detail0, detail1) -> detail0.compareByAge(detail1))
                .limit(3)
                .map(detail -> detail.getUsage())
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}
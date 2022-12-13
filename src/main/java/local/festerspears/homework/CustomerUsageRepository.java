package local.festerspears.homework;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerUsageRepository {

    public CustomerUsageReport customerUsageReport(String customerId) {
        List<MonthlyUsageDetail> custUsageDetails = Collections.unmodifiableList(List.of(
                new MonthlyUsageDetail("January", 2000, 1.5),
                new MonthlyUsageDetail("February", 2001, 0.1),
                new MonthlyUsageDetail("March", 2001, 2.1),
                new MonthlyUsageDetail("April", 2001, 3.5),
                new MonthlyUsageDetail("May", 2001, 0.0)));

        return new CustomerUsageReport(customerId, custUsageDetails);
    }
}
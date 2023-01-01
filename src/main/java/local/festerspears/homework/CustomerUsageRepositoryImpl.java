package local.festerspears.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public final class CustomerUsageRepositoryImpl {

    @Autowired
    @Lazy
    CustomerUsageRepository customerUsageRepo;

    public CustomerUsageReport customerUsageReport(long customerId) {
        return new CustomerUsageReport(customerId, customerUsageRepo.findByCustomerId(customerId));
    }
}
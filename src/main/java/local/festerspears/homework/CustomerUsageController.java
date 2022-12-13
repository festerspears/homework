package local.festerspears.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerUsageController {

    @Autowired
    private CustomerUsageRepository customerUsageRepository;

    @GetMapping("/customers/{customerId}/usage")
    public CustomerUsageReport customerUsageById(@PathVariable("customerId") String customerId) {
        return customerUsageRepository.customerUsageReport(customerId);
    }
}
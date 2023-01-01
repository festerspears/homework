package local.festerspears.homework;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUsageRepository extends JpaRepository<MonthlyUsageDetail, Long> {

    List<MonthlyUsageDetail> findByCustomerId(long customerId);

    public CustomerUsageReport customerUsageReport(long customerId);
}
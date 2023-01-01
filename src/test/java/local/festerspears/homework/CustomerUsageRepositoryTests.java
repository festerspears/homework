package local.festerspears.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import local.festerspears.homework.testutils.TestUtils;

@DataJpaTest
@Tag("integration")
@Tag("repo")
public class CustomerUsageRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerUsageRepository customerUsageRepository;

    @Test
    void customerUsageReportReturnsCorrectReportGivenAnId() {
        long testCustId = 1234;
        List<MonthlyUsageDetail> testMonthlyUsageDetails = TestUtils.generateMonthlyUsageDetailsList(10, testCustId);
        testMonthlyUsageDetails.stream().forEach(detail -> {
            testEntityManager.persistAndFlush(detail);
            testEntityManager.detach(detail);
        });

        CustomerUsageReport returnedCustomerUsageReport = customerUsageRepository.customerUsageReport(testCustId);

        assertEquals(testCustId, returnedCustomerUsageReport.getCustomerId());
        assertEquals(testMonthlyUsageDetails, returnedCustomerUsageReport.getMonthlyUsageDetails());
    }

    @Test
    void customerUsageReportReturnsReportWithCorrectNumberOfDetailsGivenAnId() {
        long testCustId = 1234;
        int testNumDetails = 10;
        List<MonthlyUsageDetail> testMonthlyUsageDetails = TestUtils.generateMonthlyUsageDetailsList(testNumDetails,
                testCustId);
        testMonthlyUsageDetails.stream().forEach(detail -> {
            testEntityManager.persistAndFlush(detail);
            testEntityManager.detach(detail);
        });

        CustomerUsageReport returnedCustomerUsageReport = customerUsageRepository.customerUsageReport(testCustId);

        assertEquals(testCustId, returnedCustomerUsageReport.getCustomerId());
        assertEquals(testNumDetails, returnedCustomerUsageReport.getMonthlyUsageDetails().size());
    }

    @Test
    void findByIdReturnsSavedMonthlyUsageDetailAfterDetach() {
        MonthlyUsageDetail testMonthlyUsageDetail = getTestMonthlyUsageDetail();
        testEntityManager.persistAndFlush(testMonthlyUsageDetail);
        testEntityManager.detach(testMonthlyUsageDetail);

        Optional<MonthlyUsageDetail> returnedMonthlyUsageDetail = customerUsageRepository
                .findById(testMonthlyUsageDetail.getId());

        assertEquals(testMonthlyUsageDetail.getId(),
                returnedMonthlyUsageDetail.get().getId());
    }

    @Test
    void savePersistsMonthlyUsageDetail() {
        MonthlyUsageDetail savedMonthlyUsageDetail = customerUsageRepository.saveAndFlush(getTestMonthlyUsageDetail());

        MonthlyUsageDetail returnedMonthlyUsageDetail = testEntityManager.find(MonthlyUsageDetail.class,
                savedMonthlyUsageDetail.getId());

        assertEquals(savedMonthlyUsageDetail.getId(), returnedMonthlyUsageDetail.getId());
    }

    @Test
    void findByIdReturnsSavedMonthlyUsageDetail() {
        Long testUsageDetailId = testEntityManager.persistAndGetId(
                getTestMonthlyUsageDetail(),
                Long.class);
        testEntityManager.flush();

        Optional<MonthlyUsageDetail> returnedMonthlyUsageDetail = customerUsageRepository.findById(testUsageDetailId);

        assertEquals(testUsageDetailId, returnedMonthlyUsageDetail.get().getId());
    }

    private MonthlyUsageDetail getTestMonthlyUsageDetail() {
        return TestUtils.generateMonthlyUsageDetailsList(1, 1234).get(0);
    }
}
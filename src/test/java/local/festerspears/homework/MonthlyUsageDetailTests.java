package local.festerspears.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MonthlyUsageDetailTests {

    private long testCustId = 1234;

    @Test
    public void compareByAgeReturnsNegativeOneWhenNewerDetailIsPassedOlderDetailByYear() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail(testCustId, "January", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail(testCustId, "January", 2022, 0.0);

        assertEquals(-1, newerDetail.compareByAge(olderDetail));
    }

    @Test
    public void compareByAgeReturnsNegativeOneWhenNewerDetailIsPassedOlderDetailByMonth() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail(testCustId, "November", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail(testCustId, "January", 2023, 0.0);

        assertEquals(-1, newerDetail.compareByAge(olderDetail));
    }

    @Test
    public void compareByAgeReturnsOneWhenOlderDetailIsPassedNewerDetailByMonth() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail(testCustId, "November", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail(testCustId, "January", 2023, 0.0);

        assertEquals(1, olderDetail.compareByAge(newerDetail));
    }

    @Test
    public void compareByAgeReturnsOneWhenOlderDetailIsPassedNewerDetailByYear() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail(testCustId, "January", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail(testCustId, "January", 2022, 0.0);

        assertEquals(1, olderDetail.compareByAge(newerDetail));
    }

    @Test
    public void compareByAgeReturnsZeroWhenDetailsAreEqual() throws Exception {
        MonthlyUsageDetail testDetail0 = new MonthlyUsageDetail(testCustId, "January", 2022, 0.0);
        MonthlyUsageDetail testDetail1 = new MonthlyUsageDetail(testCustId, "January", 2022, 0.0);

        assertEquals(0, testDetail0.compareByAge(testDetail1));
    }

    @Test
    public void newMonthlyUsageDetailHasCorrectId() throws Exception {
        MonthlyUsageDetail testUsageDetail = new MonthlyUsageDetail(testCustId, "January", 2022, 0.0);

        assertEquals(0, testUsageDetail.getId());
    }

    @Test
    public void newMonthlyUsageDetailHasCorrectCustomerId() throws Exception {
        MonthlyUsageDetail testUsageDetail = new MonthlyUsageDetail(testCustId, "January", 2022, 0.0);

        assertEquals(testCustId, testUsageDetail.getCustomerId());
    }

    @Test
    public void newMonthlyUsageDetailHasCorrectMonth() throws Exception {
        String testMonth = "January";
        MonthlyUsageDetail testUsageDetail = new MonthlyUsageDetail(testCustId, testMonth, 2022, 0.0);

        assertEquals(testMonth, testUsageDetail.getMonthOfUsage());
    }

    @Test
    public void newMonthlyUsageDetailHasCorrectYear() throws Exception {
        int testYear = 2022;
        MonthlyUsageDetail testUsageDetail = new MonthlyUsageDetail(testCustId, "January", testYear, 0.0);

        assertEquals(testYear, testUsageDetail.getYearOfUsage());
    }

    @Test
    public void newMonthlyUsageDetailHasCorrectUsage() throws Exception {
        double testUsage = 5.2;
        MonthlyUsageDetail testUsageDetail = new MonthlyUsageDetail(testCustId, "January", 2022, testUsage);

        assertEquals(testUsage, testUsageDetail.getUsage());
    }
}
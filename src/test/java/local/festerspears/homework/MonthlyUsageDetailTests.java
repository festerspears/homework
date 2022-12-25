package local.festerspears.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MonthlyUsageDetailTests {

    @Test
    public void compareByAgeReturnsNegativeOneWhenNewerDetailIsPassedOlderDetailByYear() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail("January", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail("January", 2022, 0.0);

        assertEquals(-1, newerDetail.compareByAge(olderDetail));
    }

    @Test
    public void compareByAgeReturnsNegativeOneWhenNewerDetailIsPassedOlderDetailByMonth() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail("November", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail("January", 2023, 0.0);

        assertEquals(-1, newerDetail.compareByAge(olderDetail));
    }

    @Test
    public void compareByAgeReturnsOneWhenOlderDetailIsPassedNewerDetailByMonth() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail("November", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail("January", 2023, 0.0);

        assertEquals(1, olderDetail.compareByAge(newerDetail));
    }

    @Test
    public void compareByAgeReturnsOneWhenOlderDetailIsPassedNewerDetailByYear() throws Exception {
        MonthlyUsageDetail newerDetail = new MonthlyUsageDetail("January", 2023, 0.0);
        MonthlyUsageDetail olderDetail = new MonthlyUsageDetail("January", 2022, 0.0);

        assertEquals(1, olderDetail.compareByAge(newerDetail));
    }

    @Test
    public void compareByAgeReturnsZeroWhenDetailsAreEqual() throws Exception {
        MonthlyUsageDetail testDetail0 = new MonthlyUsageDetail("January", 2022, 0.0);
        MonthlyUsageDetail testDetail1 = new MonthlyUsageDetail("January", 2022, 0.0);

        assertEquals(0, testDetail0.compareByAge(testDetail1));
    }
}
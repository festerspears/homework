package local.festerspears.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RollingAverageCalculatorTests {

    private long testCustId = 1234;

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenGreaterThanThreeMonthsOfUsageDetailsWithBigGap()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2005, 1.97));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "December", 2002, 4.27));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2005, 3.14));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "November", 2002, 0.63));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "October", 2002, 2.76));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(3.12, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenGreaterThanThreeMonthsOfUsageDetailsWithYearRollover()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2003, 1.97));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "December", 2002, 4.27));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 3.14));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "November", 2002, 0.63));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(3.12, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenGreaterThanThreeMonthsOfUsageDetailsReverseOrderedByDate()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 4.27));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2003, 0.63));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "March", 2003, 1.97));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "April", 2003, 3.14));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(1.91, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenGreaterThanThreeMonthsOfUsageDetailsUnorderedByDate()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "March", 2003, 1.97));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 4.27));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "April", 2003, 3.14));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2003, 0.63));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(1.91, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenGreaterThanThreeMonthsOfUsageDetailsOrderedByDate()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "April", 2003, 3.14));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "March", 2003, 1.97));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2003, 0.63));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 4.27));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(1.91, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenThreeMonthsOfUsageDetails()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 4.27));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2003, 0.63));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "March", 2003, 1.97));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(2.29, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenTwoMonthsOfUsageDetails() throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 4.27));
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "February", 2003, 0.63));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(2.45, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsCorrectValueWhenSingleMonthOfUsageDetails()
            throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();
        testUsageDetails.add(new MonthlyUsageDetail(testCustId, "January", 2003, 4.27));

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(4.27, result, .01);
    }

    @Test
    public void threeMonthRollingAvgReturnsZeroWhenUsageDetailsAreEmpty() throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = new ArrayList<>();

        double result = RollingAverageCalculator.threeMonthRollingAvg(testUsageDetails);

        assertEquals(0.0, result, .01);
    }
}
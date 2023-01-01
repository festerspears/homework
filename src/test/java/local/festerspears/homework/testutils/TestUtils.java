package local.festerspears.homework.testutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import local.festerspears.homework.MonthTranscoder;
import local.festerspears.homework.MonthlyUsageDetail;

public class TestUtils {

    private static double monthlyUsageMin = 0.0;
    private static double monthlyUsageMax = 10.0;
    private static Random r = new Random();
    private static int numOfMonthsInYear = 12;

    public static List<MonthlyUsageDetail> generateMonthlyUsageDetailsList(int numDetails, long custId) {
        int firstBillingMonth = r.nextInt(
                MonthTranscoder.DecemberAsInt - MonthTranscoder.JanuaryAsInt) + MonthTranscoder.JanuaryAsInt;
        int lastBillingMonth = firstBillingMonth + numDetails;
        int year = 2000;
        List<MonthlyUsageDetail> monthlyUsageDetails = new ArrayList<>();
        for (int monthAsInt : IntStream.range(firstBillingMonth, lastBillingMonth).toArray()) {
            int billingMonthAsInt = monthAsInt;
            if (billingMonthAsInt > MonthTranscoder.DecemberAsInt) {
                billingMonthAsInt = billingMonthAsInt - ((billingMonthAsInt / numOfMonthsInYear) * numOfMonthsInYear);
            }

            if (billingMonthAsInt == MonthTranscoder.JanuaryAsInt) {
                year++;
            }

            monthlyUsageDetails.add(
                    new MonthlyUsageDetail(
                            custId,
                            MonthTranscoder.toString(billingMonthAsInt),
                            year,
                            generateRandomUsage()));
        }

        return monthlyUsageDetails;
    }

    private static double generateRandomUsage() {
        return (monthlyUsageMin + (monthlyUsageMax - monthlyUsageMin) * r.nextDouble());
    }
}
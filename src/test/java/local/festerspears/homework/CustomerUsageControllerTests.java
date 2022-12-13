package local.festerspears.homework;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerUsageControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerUsageRepository mockUsageRepository;

    private String testCustId = "1234";
    private Random r = new Random();
    private int numOfMonthsInYear = 12;
    private int fiveYears = 5 * numOfMonthsInYear;

    @Test
    public void getCustomerUsageReturnsReportWithCorrectMonthlyUsageDetails() throws Exception {
        int randomNumberOfRecords = r.nextInt(fiveYears);
        CustomerUsageReport testCustomerUsageReport = new CustomerUsageReport(testCustId,
                generateMonthlyUsageDetailsList(randomNumberOfRecords));
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(testCustomerUsageReport);

        String usageResponseAsString = mockMvc.perform(mockUsageRequest(testCustId)).andReturn().getResponse()
                .getContentAsString();
        CustomerUsageReport customerUsageReportResponse = objectMapper.readValue(usageResponseAsString,
                CustomerUsageReport.class);

        assertEquals(testCustomerUsageReport.getMonthlyUsageDetails(),
                customerUsageReportResponse.getMonthlyUsageDetails(),
                String.format("*** %s ***", customerUsageReportResponse.getMonthlyUsageDetails()));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectNumberOfMonthlyUsageDetails() throws Exception {
        int randomNumberOfRecords = r.nextInt(fiveYears);
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(
                        new CustomerUsageReport(testCustId, generateMonthlyUsageDetailsList(randomNumberOfRecords)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails", hasSize(randomNumberOfRecords)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithNoMonthlyUsageDetails() throws Exception {
        int numTestMonthlyUsageDetails = 0;
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        generateMonthlyUsageDetailsList(numTestMonthlyUsageDetails)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails", hasSize(numTestMonthlyUsageDetails)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithArrayOfMonthlyUsageDetails() throws Exception {
        int numTestMonthlyUsageDetails = 5;
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        generateMonthlyUsageDetailsList(numTestMonthlyUsageDetails)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails", hasSize(numTestMonthlyUsageDetails)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectYear() throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId, generateMonthlyUsageDetailsList(1)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails.[0].year", is(2000)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCustomerId() throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId, generateMonthlyUsageDetailsList(1)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.customerId", is(testCustId)));
    }

    @Test
    public void getCustomerUsageReturns200() throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId, generateMonthlyUsageDetailsList(1)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder mockUsageRequest(String customerId) {
        return MockMvcRequestBuilders.get(String.format("/customers/%s/usage", customerId))
                .accept(MediaType.APPLICATION_JSON);
    }

    private int januaryAsInt = 0;
    private int decemberAsInt = 11;
    private double monthlyUsageMin = 0.0;
    private double monthlyUsageMax = 10.0;

    private List<MonthlyUsageDetail> generateMonthlyUsageDetailsList(int numDetails) {
        int firstBillingMonth = r.nextInt(decemberAsInt - januaryAsInt) + januaryAsInt;
        int lastBillingMonth = firstBillingMonth + numDetails;
        int year = 2000;
        List<MonthlyUsageDetail> monthlyUsageDetails = new ArrayList<>();
        for (int monthAsInt : IntStream.range(firstBillingMonth, lastBillingMonth).toArray()) {
            int billingMonthAsInt = monthAsInt;
            if (billingMonthAsInt > decemberAsInt) {
                billingMonthAsInt = billingMonthAsInt - ((billingMonthAsInt / numOfMonthsInYear) * numOfMonthsInYear);
            }

            if (billingMonthAsInt == januaryAsInt) {
                year++;
            }

            monthlyUsageDetails
                    .add(new MonthlyUsageDetail(intMonthToStringMonth(billingMonthAsInt), year, generateRandomUsage()));
        }

        return monthlyUsageDetails;
    }

    private double generateRandomUsage() {
        return (monthlyUsageMin + (monthlyUsageMax - monthlyUsageMin) * r.nextDouble());
    }

    private String intMonthToStringMonth(int month) {
        if (month < januaryAsInt || month > decemberAsInt) {
            throw new IllegalArgumentException(
                    String.format("The month argument must be between %s and %s.", januaryAsInt, decemberAsInt));
        }
        return switch (month) {
            case 0 -> "January";
            case 1 -> "February";
            case 2 -> "March";
            case 3 -> "April";
            case 4 -> "May";
            case 5 -> "June";
            case 6 -> "July";
            case 7 -> "August";
            case 8 -> "September";
            case 9 -> "October";
            case 10 -> "November";
            default -> "December";
        };
    }
}

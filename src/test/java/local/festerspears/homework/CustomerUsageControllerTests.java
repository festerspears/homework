package local.festerspears.homework;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import local.festerspears.homework.testutils.TestUtils;

@WebMvcTest(CustomerUsageController.class)
@AutoConfigureMockMvc
@Tag("integration")
public class CustomerUsageControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerUsageRepository mockUsageRepository;

    private long testCustId = 1234;
    private Random r = new Random();
    private int numOfMonthsInYear = 12;
    private int fiveYears = 5 * numOfMonthsInYear;

    @Test
    public void getCustomerUsageReturnsReportWithCorrectLastThreeMonthRollingAvgWhenNoDetails()
            throws Exception {
        List<MonthlyUsageDetail> testMonthlyUsageDetails = new ArrayList<>();
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId, testMonthlyUsageDetails));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.lastThreeMonthsRollingAvg", is(0.0)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectLastThreeMonthRollingAvg() throws Exception {
        List<MonthlyUsageDetail> testMonthlyUsageDetails = TestUtils.generateMonthlyUsageDetailsList(20, testCustId);
        double testThreeMonthRollingAvg = RollingAverageCalculator
                .threeMonthRollingAvg(testMonthlyUsageDetails);
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId, testMonthlyUsageDetails));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.lastThreeMonthsRollingAvg",
                        is(testThreeMonthRollingAvg)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectMonthlyUsageDetails()
            throws Exception {
        int randomNumberOfRecords = r.nextInt(fiveYears);
        CustomerUsageReport testCustomerUsageReport = new CustomerUsageReport(testCustId,
                TestUtils.generateMonthlyUsageDetailsList(randomNumberOfRecords, testCustId));
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(testCustomerUsageReport);

        String usageResponseAsString = mockMvc.perform(mockUsageRequest(testCustId)).andReturn().getResponse()
                .getContentAsString();
        CustomerUsageReport customerUsageReportResponse = objectMapper.readValue(usageResponseAsString,
                CustomerUsageReport.class);

        assertEquals(testCustomerUsageReport.getMonthlyUsageDetails(),
                customerUsageReportResponse.getMonthlyUsageDetails(),
                String.format("*** %s ***",
                        customerUsageReportResponse.getMonthlyUsageDetails()));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectNumberOfMonthlyUsageDetails() throws Exception {
        int randomNumberOfRecords = r.nextInt(fiveYears);
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(
                        new CustomerUsageReport(testCustId, TestUtils.generateMonthlyUsageDetailsList(
                                randomNumberOfRecords, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails",
                        hasSize(randomNumberOfRecords)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithNoMonthlyUsageDetails() throws Exception {
        int numTestMonthlyUsageDetails = 0;
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        TestUtils.generateMonthlyUsageDetailsList(numTestMonthlyUsageDetails, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails",
                        hasSize(numTestMonthlyUsageDetails)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithArrayOfMonthlyUsageDetails()
            throws Exception {
        int numTestMonthlyUsageDetails = 5;
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        TestUtils.generateMonthlyUsageDetailsList(numTestMonthlyUsageDetails, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails",
                        hasSize(numTestMonthlyUsageDetails)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithLastThreeMonthRollingAvg()
            throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        TestUtils.generateMonthlyUsageDetailsList(1, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.lastThreeMonthsRollingAvg",
                        greaterThanOrEqualTo(0.0)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectYear() throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        TestUtils.generateMonthlyUsageDetailsList(1, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.monthlyUsageDetails.[0].yearOfUsage", oneOf(2000, 2001)));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCorrectMonth() throws Exception {
        List<MonthlyUsageDetail> testUsageDetails = TestUtils.generateMonthlyUsageDetailsList(1, testCustId);
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId, testUsageDetails));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(
                        jsonPath("$.monthlyUsageDetails.[0].monthOfUsage",
                                equalTo(testUsageDetails.get(0).getMonthOfUsage())));
    }

    @Test
    public void getCustomerUsageReturnsReportWithCustomerId() throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        TestUtils.generateMonthlyUsageDetailsList(1, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(jsonPath("$.customerId", equalTo(testCustId), Long.class));
    }

    @Test
    public void getCustomerUsageReturns200() throws Exception {
        Mockito.when(mockUsageRepository.customerUsageReport(testCustId))
                .thenReturn(new CustomerUsageReport(testCustId,
                        TestUtils.generateMonthlyUsageDetailsList(1, testCustId)));

        mockMvc.perform(mockUsageRequest(testCustId))
                .andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder mockUsageRequest(long customerId) {
        return MockMvcRequestBuilders.get(String.format("/customers/%s/usage",
                customerId))
                .accept(MediaType.APPLICATION_JSON);
    }
}

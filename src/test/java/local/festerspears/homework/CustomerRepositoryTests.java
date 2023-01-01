package local.festerspears.homework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@Tag("integration")
@Tag("repo")
public class CustomerRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findByIdReturnsSavedCustomerAfterDetach() {
        Customer testCustomer = new Customer();
        testEntityManager.persistAndFlush(testCustomer);
        testEntityManager.detach(testCustomer);

        Optional<Customer> returnedCustomer = customerRepository.findById(testCustomer.getId());

        assertEquals(testCustomer.getId(), returnedCustomer.get().getId());
    }

    @Test
    void savePersistsCustomer() {
        Customer savedCustomer = customerRepository.saveAndFlush(new Customer());

        Customer returnedCustomer = testEntityManager.find(Customer.class, savedCustomer.getId());

        assertEquals(savedCustomer.getId(), returnedCustomer.getId());
    }

    @Test
    void findByIdReturnsSavedCustomer() {
        Long testCustId = testEntityManager.persistAndGetId(new Customer(), Long.class);
        testEntityManager.flush();

        Optional<Customer> returnedCustomer = customerRepository.findById(testCustId);

        assertEquals(testCustId, returnedCustomer.get().getId());
    }
}
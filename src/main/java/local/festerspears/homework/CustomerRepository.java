package local.festerspears.homework;

import org.springframework.data.jpa.repository.JpaRepository;

// TODO: Do we need this for this exercise?
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

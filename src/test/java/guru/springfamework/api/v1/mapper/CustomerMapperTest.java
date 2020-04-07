package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerMapperTest {
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setFirstName("Bob");

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertThat(customerDTO.getFirstName(), is("Bob"));
    }
}
package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerMapperTest {
    private final String BOB = "Bob";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setFirstName(BOB);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertThat(customerDTO.getFirstName(), is(BOB));
    }

    @Test
    public void customerDtoToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(BOB);

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        assertThat(customer.getFirstName(), is(BOB));
    }
}
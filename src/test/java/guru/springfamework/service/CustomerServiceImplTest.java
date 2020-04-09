package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static guru.springfamework.controller.CustomerController.BASE_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {
    private static final long ID = 1L;

    private CustomerServiceImpl customerService;

    @Mock private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();

        assertThat(customerDTOs.size(), is(3));
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("Bob");
        customer.setId(ID);
        Optional<Customer> customerOptional = Optional.of(customer);

        when(customerRepository.findById(ID)).thenReturn(customerOptional);

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertThat(customerDTO.getFirstName(), is("Bob"));
    }

    @Test
    public void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Joe");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        assertThat(savedDto.getFirstName(), is(customerDTO.getFirstName()));
        assertThat(savedDto.getCustomerUrl(), is(BASE_URL + "/1"));
    }

    @Test
    public void updateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jill");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.saveCustomerByDTO(ID, customerDTO);

        assertThat(savedDto.getFirstName(), is(customerDTO.getFirstName()));
        assertThat(savedDto.getCustomerUrl(), is(BASE_URL + "/1"));
    }

    @Test
    public void deleteCustomerById() {
        customerService.deleteCustomerById(ID);

        verify(customerRepository, times(1)).deleteById(ID);
    }
}
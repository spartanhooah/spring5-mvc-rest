package guru.springfamework.controller;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controller.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    private MockMvc mockMvc;
    private CustomerDTO bob;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        bob = new CustomerDTO();
        bob.setFirstName("Bob");
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstName("Joe");

        List<CustomerDTO> customers = Arrays.asList(bob, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(bob);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Bob")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Sue");
        customer.setLastName("Wilkins");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(customer.getFirstName());
        returnDto.setLastName(customer.getLastName());
        returnDto.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDto);

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Sue")))
                .andExpect(jsonPath("$.customerUrl", is("/api/v1/customers/1")));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Wilma");
        customer.setLastName("Flintstone");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(customer.getFirstName());
        returnDto.setLastName(customer.getLastName());
        returnDto.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(), eq(customer))).thenReturn(returnDto);

        mockMvc.perform(put("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Wilma")))
                .andExpect(jsonPath("$.customerUrl", is("/api/v1/customers/1")));
    }
}
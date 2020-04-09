package guru.springfamework.controller;

import guru.springfamework.api.v1.exception.ResourceNotFoundException;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controller.AbstractRestControllerTest.asJsonString;
import static guru.springfamework.controller.CustomerController.BASE_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    private final String BOB_FIRST_NAME = "Bob";

    private MockMvc mockMvc;
    private CustomerDTO bob;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        bob = new CustomerDTO();
        bob.setFirstName(BOB_FIRST_NAME);
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Joe");

        List<CustomerDTO> customers = Arrays.asList(bob, customerDTO);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(bob);

        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(BOB_FIRST_NAME)));
    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(bob.getFirstName());
        returnDto.setLastName(bob.getLastName());
        returnDto.setCustomerUrl(BASE_URL + "/1");

        when(customerService.createNewCustomer(bob)).thenReturn(returnDto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bob)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(BOB_FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", is(BASE_URL + "/1")));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(bob.getFirstName());
        returnDto.setLastName(bob.getLastName());
        returnDto.setCustomerUrl(BASE_URL + "/1");

        when(customerService.saveCustomerByDTO(anyLong(), eq(bob))).thenReturn(returnDto);

        mockMvc.perform(put(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bob)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(BOB_FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", is(BASE_URL + "/1")));
    }

    @Test
    public void patchCustomer() throws Exception {
        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(bob.getFirstName());
        returnDto.setLastName("Rubble");
        returnDto.setCustomerUrl(BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bob)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(BOB_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is("Rubble")))
                .andExpect(jsonPath("$.customerUrl", is(BASE_URL + "/1")));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void NotFoundException() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
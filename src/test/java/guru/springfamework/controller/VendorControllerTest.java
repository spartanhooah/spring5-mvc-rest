package guru.springfamework.controller;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.service.VendorService;
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
import static guru.springfamework.controller.VendorController.BASE_URL;
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
public class VendorControllerTest {
    private static final String BURGER_NAME = "Bob's Burgers";

    private MockMvc mockMvc;
    private VendorDTO burgers;

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        burgers = new VendorDTO();
        burgers.setName(BURGER_NAME);
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorDTO gyros = new VendorDTO();
        gyros.setName("Tom & Jerry's");

        List<VendorDTO> vendors = Arrays.asList(burgers, gyros);

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createVendor() throws Exception {
        VendorDTO returnDto = new VendorDTO();
        returnDto.setName(burgers.getName());
        returnDto.setVendorUrl(BASE_URL + "/1");

        when(vendorService.createNewVendor(burgers)).thenReturn(returnDto);

        mockMvc.perform(post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(burgers)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(BURGER_NAME)));
    }

    @Test
    public void getVendorById() throws Exception {
        when(vendorService.getVendorById(1L)).thenReturn(burgers);

        mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(BURGER_NAME)));
    }

    @Test
    public void updateVendor() throws Exception {
        VendorDTO returnDto = new VendorDTO();
        returnDto.setName(burgers.getName());
        returnDto.setVendorUrl(BASE_URL + "/1");

        when(vendorService.saveVendorByDTO(anyLong(), eq(burgers))).thenReturn(returnDto);

        mockMvc.perform(put(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(burgers)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(BURGER_NAME)))
                .andExpect(jsonPath("$.vendorUrl", is(BASE_URL + "/1")));
    }

    @Test
    public void patchVendor() throws Exception {
        VendorDTO returnDto = new VendorDTO();
        returnDto.setName("Wanda's Waffles");
        returnDto.setVendorUrl(BASE_URL + "/1");

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(burgers)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Wanda's Waffles")))
                .andExpect(jsonPath("$.vendorUrl", is(BASE_URL + "/1")));
    }

    @Test
    public void deleteVendor() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk());
    }
}
package guru.springfamework.controller;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.service.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controller.AbstractRestControllerTest.asJsonString;
import static guru.springfamework.controller.VendorController.BASE_URL;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerIT {
    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO1;
    VendorDTO vendorDTO2;

    @Before
    public void setUp() {
        vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Vendor 1");
        vendorDTO1.setVendorUrl(BASE_URL + "/1");

        vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Vendor 2");
        vendorDTO2.setVendorUrl(BASE_URL + "/2");
    }

    @Test
    public void getVendorList() throws Exception {
        List<VendorDTO> vendors = Arrays.asList(vendorDTO1, vendorDTO2);

        given(vendorService.getAllVendors()).willReturn(vendors);

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO1);

        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(vendorDTO1.getName())));
    }

    @Test
    public void createVendor() throws Exception {
        given(vendorService.createNewVendor(vendorDTO1)).willReturn(vendorDTO1);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(vendorDTO1.getName())));
    }
}

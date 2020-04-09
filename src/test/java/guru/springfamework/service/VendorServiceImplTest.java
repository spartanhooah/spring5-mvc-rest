package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static guru.springfamework.controller.VendorController.BASE_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VendorServiceImplTest {
    private static final long ID = 1L;
    private static final String NAME = "Bob's Burgers";

    private VendorServiceImpl vendorService;

    @Mock private VendorRepository vendorRepository;

    @Before
    public void setUp() {
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOs = vendorService.getAllVendors();

        assertThat(vendorDTOs.size(), is(3));
    }

    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        Optional<Vendor> vendorOptional = Optional.of(vendor);

        when(vendorRepository.findById(ID)).thenReturn(vendorOptional);

        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertThat(vendorDTO.getName(), is(NAME));
    }

    @Test
    public void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(NAME);
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO returnDto = vendorService.createNewVendor(vendorDTO);

        assertThat(returnDto.getName(), is(NAME));
        assertThat(returnDto.getVendorUrl(), is(BASE_URL + "/1"));
    }

    @Test
    public void updateVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(NAME);
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDTO = vendorService.saveVendorByDTO(ID, vendorDTO);

        assertThat(savedDTO.getName(), is(NAME));
        assertThat(savedDTO.getVendorUrl(), is(BASE_URL + "/1"));
    }

    @Test
    public void deleteVendor() {
        vendorService.deleteVendorById(ID);

        verify(vendorRepository, times(1)).deleteById(ID);
    }
}
package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VendorMapperTest {
    private final String NAME = "Bob's Burgers";
    VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);

        assertThat(vendorDTO.getName(), is(NAME));
    }

    @Test
    public void vendorDtoToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = mapper.vendorDtoToVendor(vendorDTO);

        assertThat(vendor.getName(), is(NAME));
    }
}
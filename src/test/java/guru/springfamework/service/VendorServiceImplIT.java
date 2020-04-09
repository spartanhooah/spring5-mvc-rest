package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {
    @Autowired private VendorRepository vendorRepository;

    @Autowired private CustomerRepository customerRepository;

    @Autowired private CategoryRepository categoryRepository;
    
    private VendorService vendorService;

    @Before
    public void setUp() {
        System.out.println("Loading vendor data");
        System.out.println(vendorRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);

        bootstrap.run();

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void patchVendor() {
        String updatedName = "updated name";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertThat(originalVendor, is(notNullValue()));
        // save original name
        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).get();

        assertThat(updatedVendor, is(notNullValue()));
        assertThat(updatedVendor.getName(), is(updatedName));
    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();

        System.out.println("Vendors found: " + vendors.size());

        return vendors.get(0).getId();
    }
}

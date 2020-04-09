package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) {
        buildCategories();
        buildCustomers();
        buildVendors();
    }

    private void buildCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.saveAll(asList(fruits, dried, fresh, exotic, nuts));
    }

    private void buildCustomers() {
        Customer mal = new Customer();
        mal.setFirstName("Malcolm");
        mal.setLastName("Reynolds");

        Customer inara = new Customer();
        inara.setFirstName("Inara");
        inara.setLastName("Serra");

        Customer kaylee = new Customer();
        kaylee.setFirstName("Kaylee");
        kaylee.setLastName("Frye");

        customerRepository.saveAll(asList(mal, inara, kaylee));
    }

    private void buildVendors() {
        Vendor burgers = new Vendor();
        burgers.setName("Bob's Burgers");

        Vendor gyros = new Vendor();
        gyros.setName("Tom & Jerry's");

        vendorRepository.saveAll(asList(burgers, gyros));
    }
}

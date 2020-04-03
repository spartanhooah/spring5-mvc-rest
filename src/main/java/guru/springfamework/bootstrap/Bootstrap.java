package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        buildCategories();
        buildCustomers();

        System.out.println("Data loaded: " + categoryRepository.count());
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

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
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

        customerRepository.saveAll(Arrays.asList(mal, inara, kaylee));
    }
}

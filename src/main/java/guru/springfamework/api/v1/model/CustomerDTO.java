package guru.springfamework.api.v1.model;

import lombok.Data;

import java.net.URL;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private URL customerUrl;
}

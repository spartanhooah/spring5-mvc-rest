package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @ApiModelProperty(value = "This is the customer's first name", required = true)
    private String firstName;

    @ApiModelProperty(value = "This is the customer's first name", required = true)
    private String lastName;
    private String customerUrl;
}

package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    @ApiModelProperty(value = "The name of this vendor")
    private String name;

    @ApiModelProperty(value = "A URL to retrieve the vendor, for HATEOAS")
    private String vendorUrl;
}

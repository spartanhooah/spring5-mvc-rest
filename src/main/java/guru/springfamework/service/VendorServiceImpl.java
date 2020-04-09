package guru.springfamework.service;

import guru.springfamework.api.v1.exception.ResourceNotFoundException;
import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static guru.springfamework.controller.VendorController.BASE_URL;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    private final VendorMapper mapper;
    private final VendorRepository repository;

    @Override
    public List<VendorDTO> getAllVendors() {
        return repository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor));

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return repository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor));

                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(mapper.vendorDtoToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = mapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(long id, VendorDTO vendorDTO) {
        return repository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnDto = mapper.vendorToVendorDTO(repository.save(vendor));
            returnDto.setVendorUrl(getVendorUrl(vendor));

            return returnDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        repository.deleteById(id);
    }

    private String getVendorUrl(Vendor vendor) {
        return BASE_URL + "/" + vendor.getId();
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = repository.save(vendor);

        VendorDTO returnDto = mapper.vendorToVendorDTO(savedVendor);

        returnDto.setVendorUrl(getVendorUrl(savedVendor));

        return returnDto;
    }
}

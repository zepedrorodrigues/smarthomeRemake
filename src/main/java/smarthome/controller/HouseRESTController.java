package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.HouseIdDTO;
import smarthome.mapper.mapper.HouseMapper;
import smarthome.service.IHouseService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing house.
 */
@RestController
@RequestMapping("/houses")
public class HouseRESTController {


    private final IHouseService houseService;
    private final HouseMapper houseMapper;

    /**
     * Constructor for the HouseRESTController.
     *
     * @param houseService the house service
     * @param houseMapper  the house mapper
     */
    @Autowired
    public HouseRESTController(IHouseService houseService, HouseMapper houseMapper) {
        this.houseService = houseService;
        this.houseMapper = houseMapper;
    }

    /**
     * Get the available countries for the house location.
     *
     * @return the response entity with the list of available countries
     * or a bad request if the countries could not be retrieved
     */
    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAvailableCountries() {
        try {
            List<String> countries = houseService.getAvailableCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Configures the house location with the given location.
     *
     * @param houseDTO the house data to be updated
     * @return the response entity with the updated house data
     * or a bad request if the house could not be updated
     * or an unprocessable entity if the request is malformed
     */
    @PutMapping("/{houseId}/location")
    public ResponseEntity<HouseDTO> configHouseLocation(@PathVariable("houseId") String houseId,
                                                        @RequestBody HouseDTO houseDTO) {
        if (houseDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HouseName houseName = new HouseName(houseId);
            Location location = houseMapper.toLocation(houseDTO);
            House updatedHouse = houseService.configHouseLocation(houseName, location);
            if (updatedHouse != null) {
                HouseDTO updatedHouseDTO = houseMapper.toHouseDTO(updatedHouse);
                addLinksToHouseDTO(updatedHouseDTO);

                return new ResponseEntity<>(updatedHouseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the available house ids.
     *
     * @return the response entity with the list of available house ids
     */
    @GetMapping()
    public ResponseEntity<List<HouseIdDTO>> getHouses() {
        try {
            Iterable<HouseName> houseIds = houseService.getHouseIds();
            if (!houseIds.iterator().hasNext()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } else {
                List<HouseIdDTO> houseIdDTOS = houseMapper.toHouseIdsDTO(houseIds);
                for (HouseIdDTO houseIdDTO : houseIdDTOS) {
                    addLinksToHouseIdDTO(houseIdDTO);
                }

                return new ResponseEntity<>(houseIdDTOS, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the house.
     *
     * @param id the house id
     * @return the response entity with the house data or not found if the house does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable("id") String id) {
        try {
            HouseName houseId = new HouseName(id);
            Optional<House> house = houseService.getHouse(houseId);
            if (house.isPresent()) {
                HouseDTO houseDTO = houseMapper.toHouseDTO(house.get());
                addLinksToHouseDTO(houseDTO);

                return new ResponseEntity<>(houseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Add a house.
     *
     * @param houseDTO the house data to be added
     * @return the response entity with the added house data
     */
    @PostMapping()
    public ResponseEntity<HouseDTO> addHouse(@RequestBody HouseDTO houseDTO) {
        if (houseDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HouseName houseId = houseMapper.toHouseName(houseDTO);
            Location location = houseMapper.toLocation(houseDTO);
            House savedHouse = houseService.addHouse(houseId, location);
            if (savedHouse != null) {
                HouseDTO savedHouseDTO = houseMapper.toHouseDTO(savedHouse);
                addLinksToHouseDTO(savedHouseDTO);

                return new ResponseEntity<>(savedHouseDTO, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Add links to the house DTO.
     *
     * @param houseDTO the house DTO
     */
    private void addLinksToHouseDTO(HouseDTO houseDTO) {
        houseDTO
                .add(linkTo(methodOn(HouseRESTController.class)
                        .getHouseById(houseDTO.getHouseName())).withSelfRel())
                .add(linkTo(methodOn(HouseRESTController.class)
                        .configHouseLocation(houseDTO.getHouseName(), null))
                        .withRel("configure-house-location"));
    }

    /**
     * Add links to the house id DTO.
     *
     * @param houseIdDTO the house id DTO
     */
    private void addLinksToHouseIdDTO(HouseIdDTO houseIdDTO) {
        houseIdDTO
                .add(linkTo(methodOn(HouseRESTController.class)
                        .getHouseById(houseIdDTO.getHouseId())).withSelfRel())
                .add(linkTo(methodOn(HouseRESTController.class)
                        .configHouseLocation(houseIdDTO.getHouseId(), null))
                        .withRel("configure-house-location"));
    }
}

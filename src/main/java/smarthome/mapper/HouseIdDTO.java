package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data transfer object for the id of a house.
 */
public class HouseIdDTO extends RepresentationModel<HouseIdDTO> {


    String houseId;

    /**
     * Constructor for the HouseIdDTO.
     *
     * @param houseId the name of the house
     */
    public HouseIdDTO(String houseId) {
        this.houseId = houseId;
    }

    /**
     * Get id of the house.
     *
     * @return the id of the house
     */
    public String getHouseId() {
        return houseId;
    }


}

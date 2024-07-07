package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data transfer object for the id of a reading.
 */
public class ReadingIdDTO extends RepresentationModel<ReadingIdDTO> {

    private final String readingId;

    /**
     * Constructor for the ReadingIdDTO.
     *
     * @param id the id of the reading
     */
    public ReadingIdDTO(String id) {
        this.readingId = id;
    }

    /**
     * Get id of the reading.
     *
     * @return the id of the reading
     */
    public String getReadingId() {
        return this.readingId;
    }
}

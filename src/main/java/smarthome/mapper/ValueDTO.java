package smarthome.mapper;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data transfer object for the value.
 */
@Getter
public class ValueDTO extends RepresentationModel<ValueDTO> {


    private final String value;

    /**
     * Constructs a new ValueDTO with the specified value.
     *
     * @param value the value
     */
    public ValueDTO(String value) {
        this.value = value;
    }



}


package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.ReadingIdDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping readings to reading DTOs.
 */
@Component
public class ReadingMapper {

    /**
     * Constructs a new ReadingMapper.
     */
    public ReadingMapper() {
        // Empty constructor
    }

    /**
     * Converts a reading to a reading DTO.
     *
     * @param reading the reading to convert
     * @return the converted reading DTO
     */
    public ReadingDTO toReadingDTO(Reading reading) {
        String readingId = reading.getIdentity().getId();
        String value = reading.getValue().valueToString();
        String sensorId = reading.getSensorId().getSensorId();
        String timeStamp = reading.getTime().valueToString();

        return new ReadingDTO(readingId, sensorId, value, timeStamp);

    }

    /**
     * Converts a list of readings to a list of reading DTOs.
     *
     * @param readings the readings to convert
     * @return the converted list of reading DTOs
     */
    public List<ReadingDTO> toReadingsDTO(Iterable<Reading> readings) {
        List<ReadingDTO> readingsDTO = new ArrayList<>();
        for(Reading reading : readings) {
            readingsDTO.add(toReadingDTO(reading));
        }

        return readingsDTO;
    }

    /**
     * Converts a list of reading IDs to a list of reading ID DTOs.
     *
     * @param readingIds the reading IDs to convert
     * @return the converted list of reading ID DTOs
     */
    public List<ReadingIdDTO> toReadingIdsDTO(Iterable<ReadingId> readingIds) {
        List<ReadingIdDTO> readingIdsDTO = new ArrayList<>();
        for(ReadingId readingId : readingIds) {
            readingIdsDTO.add(new ReadingIdDTO(readingId.getId()));
        }
        return readingIdsDTO;
    }
}

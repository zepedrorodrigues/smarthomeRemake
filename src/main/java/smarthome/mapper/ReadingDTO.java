package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * The ReadingDTO class represents a data transfer object for readings.
 * It provides methods to get the unique identifier of the reading, the sensor that produced the reading,
 * the value of the reading, and the timestamp of the reading.
 */
public class ReadingDTO extends RepresentationModel<ReadingDTO> {

    private final String id;

    private final String sensorId;

    private final String readingValue;

    private final String timestamp;

    /**
     * Constructs a new ReadingDTO with the given parameters.
     *
     * @param id        the unique identifier of the reading
     * @param sensorId  the unique identifier of the sensor that produced the reading
     * @param value     the value of the reading
     * @param timestamp the timestamp of the reading
     */
    public ReadingDTO(String id, String sensorId, String value, String timestamp) {
        this.id = id;
        this.sensorId = sensorId;
        this.readingValue = value;
        this.timestamp = timestamp;
    }

    /**
     * Returns the unique identifier of the reading.
     *
     * @return the unique identifier of the reading
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the unique identifier of the sensor that produced the reading.
     *
     * @return the unique identifier of the sensor
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * Returns the value of the reading.
     *
     * @return the value of the reading
     */
    public String getReadingValue() {
        return readingValue;
    }

    /**
     * Returns the timestamp of the reading.
     *
     * @return the timestamp of the reading
     */
    public String getTimestamp() {
        return timestamp;
    }
}

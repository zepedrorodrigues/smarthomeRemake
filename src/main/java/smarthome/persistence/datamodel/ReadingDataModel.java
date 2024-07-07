package smarthome.persistence.datamodel;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.reading.Reading;

import java.time.LocalDateTime;

/**
 * ReadingDataModel is a data model that represents the Reading entity in the database.
 * It is annotated as an Entity, meaning it can be persisted to the database.
 */

@Entity
@Table(name = "READING")

public class ReadingDataModel {
    @Id
    private String readingId;
    private String sensorId;
    private String readingValue;
    private LocalDateTime timeStamp;

    /**
     * Empty constructor of the Reading Data Model
     */
    public ReadingDataModel() {
    }

    /**
     * Constructs a new ReadingDataModel from a Reading domain object.
     *
     * @param reading the Reading domain object to construct the ReadingDataModel from.
     * @throws IllegalArgumentException if the provided Reading object is null.
     */
    public ReadingDataModel(Reading reading) {
        if (reading == null) {
            throw new IllegalArgumentException();
        }
        this.readingId = reading.getIdentity().toString();
        this.sensorId = reading.getSensorId().getSensorId();
        this.readingValue = reading.getValue().valueToString();
        this.timeStamp = reading.getTime().getValue();
    }

    /**
     * Get the reading ID
     *
     * @return the reading ID
     */
    public String getReadingId() {
        return readingId;
    }

    /**
     * Returns the sensor ID associated with the reading.
     *
     * @return the sensor ID.
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * Get the value of the reading
     *
     * @return the reading value
     */

    public String getReadingValue() {
        return readingValue;
    }

    /**
     * Get the timestamp of the reading
     *
     * @return the timestamp
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}

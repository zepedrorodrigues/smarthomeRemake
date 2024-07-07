package smarthome.domain.reading;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;

import java.util.UUID;

/**
 * The Reading class represents a reading from a sensor in a smart home system.
 * Each reading has a unique ID, a reading value, a sensor ID, and a timestamp.
 */
public class Reading implements AggregateRoot<ReadingId> {

    private final ReadingId readingId;
    private final Value value;
    private final SensorId sensorId;
    private final TimeStamp timeStamp;

    /**
     * Constructs a new Reading with the given value, sensor ID, and timestamp.
     * The reading ID is automatically generated.
     *
     * @param value the value of the reading
     * @param sensorId the ID of the sensor that produced the reading
     * @param timeStamp the timestamp of when the reading was taken
     */
    protected Reading(Value value, SensorId sensorId, TimeStamp timeStamp) {
        this(null, value, sensorId, timeStamp);
    }

    /**
     * Constructs a new Reading with the given reading ID, value, sensor ID, and timestamp.
     * If the reading ID is null, a new one is automatically generated.
     * If the value, sensor ID, or timestamp is null, an IllegalArgumentException is thrown.
     *
     * @param readingId the unique ID of the reading. If null, a new ID is generated.
     * @param value the value of the reading. Must not be null.
     * @param sensorId the ID of the sensor that produced the reading. Must not be null.
     * @param timeStamp the timestamp of when the reading was taken. Must not be null.
     * @throws IllegalArgumentException if value, sensorId, or timeStamp is null.
     */
    protected Reading(ReadingId readingId, Value value, SensorId sensorId, TimeStamp timeStamp) {
        if(value == null || sensorId == null || timeStamp == null){
            throw new IllegalArgumentException();
        }

        this.readingId = readingId == null ? new ReadingId(UUID.randomUUID().toString()) : readingId;

        this.value = value;
        this.sensorId = sensorId;
        this.timeStamp = timeStamp;
    }

    /**
     * Returns the unique ID of the reading.
     *
     * @return the reading's ID
     */
    public ReadingId getIdentity() {
        return readingId;
    }

    /**
     * Returns the value of the reading.
     *
     * @return the reading's value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Returns the ID of the sensor that produced the reading.
     *
     * @return the sensor's ID
     */
    public SensorId getSensorId() {
        return sensorId;
    }

    /**
     * Returns the timestamp of when the reading was taken.
     *
     * @return the reading's timestamp
     */
    public TimeStamp getTime() {
        return timeStamp;
    }

    /**
     * Checks if the provided object is equal to this Reading.
     * The comparison is based on the unique reading ID.
     *
     * @param object the object to compare with this Reading
     * @return true if the object is this Reading or if it is a Reading with the same ID, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Reading reading = (Reading) object;
        return readingId.equals(reading.readingId);
    }

    /**
     * Returns a hash code value for this Reading.
     * The hash code is based on the unique reading ID.
     *
     * @return a hash code value for this Reading
     */
    @Override
    public int hashCode() {
        return readingId.hashCode();
    }
}

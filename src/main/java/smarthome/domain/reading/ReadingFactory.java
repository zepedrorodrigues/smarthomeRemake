package smarthome.domain.reading;

import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;

/**
 * ReadingFactory is an interface for creating Reading objects.
 * It provides methods to create a Reading with or without a ReadingId.
 */
public interface ReadingFactory {

    /**
     * Creates a Reading object without a ReadingId.
     *
     * @param value     the Value of the Reading.
     * @param sensorId  the SensorId associated with the Reading.
     * @param timeStamp the TimeStamp of the Reading.
     * @return a new Reading object.
     */
    Reading createReading(Value value, SensorId sensorId, TimeStamp timeStamp);


    /**
     * Creates a Reading object with a ReadingId.
     *
     * @param readingId the ReadingId of the Reading.
     * @param value     the Value of the Reading.
     * @param sensorId  the SensorId associated with the Reading.
     * @param timeStamp the TimeStamp of the Reading.
     * @return a new Reading object.
     */
    Reading createReading(ReadingId readingId, Value value, SensorId sensorId, TimeStamp timeStamp);
}

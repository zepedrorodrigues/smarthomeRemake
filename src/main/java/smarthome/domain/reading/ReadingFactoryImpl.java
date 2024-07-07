package smarthome.domain.reading;

import org.springframework.stereotype.Component;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;

/**
 * ReadingFactoryImpl is a class that implements the ReadingFactory interface.
 * It provides methods to create a Reading with or without a ReadingId.
 */
@Component
public class ReadingFactoryImpl implements ReadingFactory {

    /**
     * Creates a Reading object without a ReadingId.
     * This method takes in a Value, SensorId, and TimeStamp to create a new Reading.
     * The Value is converted to a string before being passed to the Reading constructor.
     *
     * @param value     the Value of the Reading, converted to a string.
     * @param sensorId  the SensorId associated with the Reading.
     * @param timeStamp the TimeStamp of the Reading.
     * @return a new Reading object.
     */
    @Override
    public Reading createReading(Value value, SensorId sensorId, TimeStamp timeStamp) {
        return new Reading(new ReadingValue(value.valueToString()), sensorId, timeStamp);
    }

    /**
     * Creates a Reading object with a ReadingId.
     * This method takes in a ReadingId, Value, SensorId, and TimeStamp to create a new Reading.
     * The Value is converted to a string before being passed to the Reading constructor.
     *
     * @param readingId the ReadingId of the Reading.
     * @param value     the Value of the Reading, converted to a string.
     * @param sensorId  the SensorId associated with the Reading.
     * @param timeStamp the TimeStamp of the Reading.
     * @return a new Reading object.
     */
    @Override
    public Reading createReading(ReadingId readingId, Value value, SensorId sensorId, TimeStamp timeStamp) {
        return new Reading(readingId, new ReadingValue(value.valueToString()), sensorId, timeStamp);
    }
}




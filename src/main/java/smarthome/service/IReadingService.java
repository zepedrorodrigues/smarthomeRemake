package smarthome.service;

import smarthome.domain.actuator.vo.DecimalValue;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.values.Value;

import java.util.List;
import java.util.Optional;

/**
 * IReadingService interface for methods related to the Reading entity.
 */
public interface IReadingService {

    /**
     * Returns a list of readings from a device in a given period.
     *
     * @param deviceId The id of the device.
     * @param start    The start time of the period.
     * @param end      The end time of the period.
     * @return A list of readings from the device in the given period.
     */
    List<Reading> getReadingsFromDeviceInAGivenPeriod(DeviceId deviceId, TimeStamp start, TimeStamp end);

    /**
     * Returns a list of readings from a device in a given period.
     *
     * @param deviceId The id of the device
     * @param start    The start time of the period.
     * @param end      The end time of the period.
     * @return A list of reading IDs from the device in the given period.
     */
    List<ReadingId> getReadingIdsFromDeviceInAGivenPeriod(DeviceId deviceId, TimeStamp start, TimeStamp end);

    /**
     * Returns the maximum instant temperature difference between two devices.
     *
     * @param deviceId1       The id of the first device.
     * @param deviceId2       The id of the second device.
     * @param start           The start time of the period.
     * @param end             The end time of the period.
     * @return The maximum instant difference between the readings of the two devices.
     */
    Value getMaxInstantTemperatureDifferenceInAGivenPeriod(DeviceId deviceId1, DeviceId deviceId2,
                                                           TimeStamp start, TimeStamp end);

    /**
     * Returns a reading by its id.
     *
     * @param id The id of the reading.
     * @return The reading with the given id.
     */
    Optional<Reading> getReading(ReadingId id);

    /**
     * Returns the peak power consumption in a given period.
     *
     * @param start The start time of the period.
     * @param end   The end time of the period.
     * @return The peak power consumption in the given period.
     */
    DecimalValue getPeakPowerConsumptionInAGivenPeriod(TimeStamp start, TimeStamp end);

}

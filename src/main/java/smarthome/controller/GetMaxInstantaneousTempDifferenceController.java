package smarthome.controller;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ValueDTO;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.mapper.mapper.ValueMapper;
import smarthome.service.IReadingService;

/**
 * GetMaxInstantaneousTempDifferenceController is a controller for getting the maximum instantaneous temperature
 * difference between two devices in a given period.
 */
public class GetMaxInstantaneousTempDifferenceController {


    private final IReadingService readingService;
    private final PeriodMapper periodMapper;
    private final ValueMapper valueMapper;

    /**
     * Initializes the controller with the given reading service.
     *
     * @param readingService    the reading service.
     * @param periodMapper      the period mapper.
     * @param valueMapper the sensor model mapper.
     */
    public GetMaxInstantaneousTempDifferenceController(IReadingService readingService, PeriodMapper periodMapper,
                                                       ValueMapper valueMapper) {
        if (readingService == null || periodMapper == null || valueMapper == null) {
            throw new IllegalArgumentException();
        }
        this.readingService = readingService;
        this.periodMapper = periodMapper;
        this.valueMapper = valueMapper;
    }

    /**
     * Returns the maximum instantaneous temperature difference between two devices in a given period.
     *
     * @param deviceId    the id of the first device.
     * @param deviceId2   the id of the second device.
     * @param periodDTO   the period in which the temperature difference will be calculated.
     * @return the maximum instantaneous temperature difference between the two devices in the given period.
     */
    public ValueDTO getMaxTemperatureDifference(String deviceId, String deviceId2,
                                                PeriodDTO periodDTO) {
        if (deviceId == null || deviceId2 == null || periodDTO == null) {
            return null;
        }
        try {
            DeviceId deviceId1VO = new DeviceId(deviceId);
            DeviceId deviceId2VO = new DeviceId(deviceId2);
            TimeStamp start = periodMapper.toStart(periodDTO);
            TimeStamp end = periodMapper.toEnd(periodDTO);
            // Get the maximum temperature difference

            Value maxInstantaneousTemperatureDifference = readingService.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                    deviceId1VO, deviceId2VO, start, end);

            if (maxInstantaneousTemperatureDifference == null) {
                return null;
            }
            return valueMapper.toDTO(maxInstantaneousTemperatureDifference);
        } catch (Exception e) {
            return null;
        }
    }
}


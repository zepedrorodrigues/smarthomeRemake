package smarthome.service;

import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;

/**
 * Service for reading data from devices.
 * <p>
 * The IReadingService interface provides methods for reading data from devices.
 */
public interface IReadingService {

    /**
     * Returns the maximum instant temperature difference between two devices.
     */
    Double getMaxInstantTemperatureDifference(DeviceDTO deviceDTO1, DeviceDTO deviceDTO2, PeriodDTO periodDTO);
}

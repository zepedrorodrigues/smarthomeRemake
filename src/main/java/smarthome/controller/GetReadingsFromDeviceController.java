package smarthome.controller;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.mapper.mapper.ReadingMapper;
import smarthome.service.IReadingService;

import java.util.List;

/**
 * This class represents the controller responsible for getting readings from a device in a given period.
 * The main method of this class is getReadingsFromDeviceInAGivenPeriod, which receives a deviceDTO and a periodDTO
 * and returns a list of readingDTOs.
 */
public class GetReadingsFromDeviceController {

    private final IReadingService readingService;
    private final ReadingMapper readingMapper;
    private final PeriodMapper periodMapper;
    private final DeviceMapper deviceMapper;

    /**
     * Constructor of the GetReadingsFromDeviceController.
     *
     * @param readingService the service that will be used to get the readings.
     * @param readingMapper the mapper that will be used to map the readings.
     * @param periodMapper  the mapper that will be used to map the period.
     * @param deviceMapper  the mapper that will be used to map the device.
     */
    public GetReadingsFromDeviceController(IReadingService readingService, ReadingMapper readingMapper,
                                           PeriodMapper periodMapper, DeviceMapper deviceMapper) {
        this.readingService = readingService;
        this.readingMapper = readingMapper;
        this.periodMapper = periodMapper;
        this.deviceMapper = deviceMapper;
    }

    /**
     * This method receives a deviceDTO and a periodDTO and returns a list of readingDTOs.
     *
     * @param deviceDTO the device from which the readings will be retrieved.
     * @param periodDTO the period in which the readings will be retrieved.
     * @return a list of readingDTOs or null if the parameters are invalid.
     */
    public List<ReadingDTO> getReadingsFromDeviceInAGivenPeriod(DeviceDTO deviceDTO, PeriodDTO periodDTO) {
        if (deviceDTO == null || periodDTO == null) {
            return null;
        }
        try {
            DeviceId deviceId = deviceMapper.toDeviceId(deviceDTO);
            TimeStamp start = periodMapper.toStart(periodDTO);
            TimeStamp end = periodMapper.toEnd(periodDTO);
            List<Reading> readings = readingService.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);
            if (readings == null) {
                return null;
            }
            return readingMapper.toReadingsDTO(readings);
        } catch (Exception e) {
            return null;
        }
    }
}
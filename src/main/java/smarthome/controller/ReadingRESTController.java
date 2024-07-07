package smarthome.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.actuator.vo.DecimalValue;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.ReadingIdDTO;
import smarthome.mapper.ValueDTO;
import smarthome.mapper.mapper.ReadingMapper;
import smarthome.mapper.mapper.ValueMapper;
import smarthome.service.IReadingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST Controller for managing readings.
 */
@RestController
@RequestMapping("/readings")
public class ReadingRESTController {

    private final IReadingService readingService;
    private final ReadingMapper readingMapper;
    private final ValueMapper valueMapper;

    /**
     * Constructor for the ReadingRESTController.
     *
     * @param readingService the service to manage the readings.
     * @param readingMapper  the mapper of the readings.
     * @param valueMapper    the mapper of the values.
     */
    @Autowired
    public ReadingRESTController(IReadingService readingService, ReadingMapper readingMapper, ValueMapper valueMapper) {
        this.readingService = readingService;
        this.readingMapper = readingMapper;
        this.valueMapper = valueMapper;
    }

    /**
     * This method receives a deviceID and a period and returns the reading IDs of the device in the given period.
     *
     * @param id          the id of the device.
     * @param startPeriod the start of the period.
     * @param endPeriod   the end of the period.
     * @return a list of readingIdDTOs or null if the parameters are invalid.
     */
    @GetMapping("/device/{id}")
    public ResponseEntity<List<ReadingIdDTO>> getReadingsFromDeviceInAGivenPeriod(@PathVariable("id") String id,
                                                                                  @RequestParam("startPeriod") String startPeriod,
                                                                                  @RequestParam("endPeriod") String endPeriod) {
        try {
            DeviceId deviceId = new DeviceId(id);
            TimeStamp start = new TimeStamp(LocalDateTime.parse(startPeriod));
            TimeStamp end = new TimeStamp(LocalDateTime.parse(endPeriod));
            List<ReadingId> readingIds = readingService.getReadingIdsFromDeviceInAGivenPeriod(
                    deviceId, start, end);
            if (readingIds == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (readingIds.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<ReadingIdDTO> readingIdDTOs = readingMapper.toReadingIdsDTO(readingIds);
            for (ReadingIdDTO readingIdDTO : readingIdDTOs) {
                readingIdDTO.add(linkTo(methodOn(ReadingRESTController.class)
                        .getReading(readingIdDTO.getReadingId())).withSelfRel());
            }
            return new ResponseEntity<>(readingIdDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * This method returns the maximum temperature difference between the two devices in the given period.
     *
     * @param deviceId    the first device to compare.
     * @param deviceId2   the second device to compare.
     * @param startPeriod the start of the period.
     * @param endPeriod   the end of the period.
     * @return the maximum temperature difference between the two devices in the given period or null if the parameters
     * are invalid or the maximum temperature difference could not be calculated.
     */
    @GetMapping("/device/{deviceId}/max-temperature-difference/{deviceId2}")
    public ResponseEntity<ValueDTO> getMaxTemperatureDifference(@PathVariable("deviceId") String deviceId,
                                                                @PathVariable("deviceId2") String deviceId2,
                                                                @RequestParam("startPeriod") String startPeriod, @RequestParam("endPeriod") String endPeriod) {
        try {
            DeviceId deviceId1VO = new DeviceId(deviceId);
            DeviceId deviceId2VO = new DeviceId(deviceId2);
            TimeStamp start = new TimeStamp(LocalDateTime.parse(startPeriod));
            TimeStamp end = new TimeStamp(LocalDateTime.parse(endPeriod));

            // Get the maximum temperature difference
            Value maxInstantaneousTemperatureDifference = readingService.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                    deviceId1VO, deviceId2VO, start, end);

            if (maxInstantaneousTemperatureDifference != null) {

                ValueDTO result = valueMapper.toDTO(maxInstantaneousTemperatureDifference);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Get the house.
     *
     * @param id the reading id
     * @return the response entity with the reading data or not found if the reading does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReadingDTO> getReading(@PathVariable("id") String id) {
        try {
            ReadingId readingId = new ReadingId(id);
            Optional<Reading> reading = readingService.getReading(readingId);
            if (reading.isPresent()) {
                ReadingDTO readingDTO = readingMapper.toReadingDTO(reading.get())
                        .add(linkTo(methodOn(ReadingRESTController.class)
                                .getReading(id)).withSelfRel());
                return new ResponseEntity<>(readingDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the peak power consumption in a given period.
     *
     * @param start the start of the period.
     * @param end   the end of the period.
     * @return the response entity with the peak power consumption, not found if the peak power consumption could not
     * be calculated or bad request if the parameters are invalid.
     */
    @GetMapping(value="/peakPowerConsumption", produces={"application/hal+json"})
    public ResponseEntity<ValueDTO> getPeakPowerConsumptionInAGivenPeriod(
            @RequestParam("start") String start, @RequestParam("end") String end) {

        TimeStamp startP;
        TimeStamp entP;

        try {
            startP = new TimeStamp(LocalDateTime.parse(start));
            entP = new TimeStamp(LocalDateTime.parse(end));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DecimalValue peakPowerConsumption = readingService.getPeakPowerConsumptionInAGivenPeriod(startP, entP);

        if(peakPowerConsumption == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ValueDTO peakPowerConsumptionDTO = valueMapper.toDTO(peakPowerConsumption);

        return new ResponseEntity<>(peakPowerConsumptionDTO, HttpStatus.OK);
    }


}

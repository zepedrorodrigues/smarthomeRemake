package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.mapper.PeriodDTO;

import java.time.LocalDateTime;

/**
 * Mapper class for PeriodDTO
 */
@Component
public class PeriodMapper {

    /**
     * Converts the start data in PeriodDTO to a LocalDateTime object
     *
     * @param periodDTO PeriodDTO
     * @return the TimeStamp of the start data
     */
    public TimeStamp toStart(PeriodDTO periodDTO) {
        return new TimeStamp(LocalDateTime.parse(periodDTO.getStart()));
    }

    /**
     * Converts the end data in PeriodDTO to a LocalDateTime object
     *
     * @param periodDTO PeriodDTO
     * @return the TimeStamp of the end data
     */
    public TimeStamp toEnd(PeriodDTO periodDTO) {
        return new TimeStamp(LocalDateTime.parse(periodDTO.getEnd()));
    }
}

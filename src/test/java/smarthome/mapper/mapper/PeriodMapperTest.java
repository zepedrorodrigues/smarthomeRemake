package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.mapper.PeriodDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is responsible for testing the PeriodMapper class.
 * It tests the conversion of start and end data in PeriodDTO to a LocalDateTime object.
 */
class PeriodMapperTest {

    PeriodMapper periodMapper;

    PeriodDTO periodDTO;

    TimeStamp timeStamp;

    /**
     * This method sets up the testing environment before each test.
     * It initializes the PeriodMapper instance and the mocks.
     */
    @BeforeEach
    void setUp() {
        periodMapper = new PeriodMapper();

        periodDTO = mock(PeriodDTO.class);
        when(periodDTO.getStart()).thenReturn("2021-01-01T00:00:00");
        when(periodDTO.getEnd()).thenReturn("2021-01-01T00:00:00");

        timeStamp = mock(TimeStamp.class);

        LocalDateTime localDateTime = LocalDateTime.parse("2021-01-01T00:00:00");
        when(timeStamp.getValue()).thenReturn(localDateTime);
    }

    /**
     * This test checks the toStart method of the PeriodMapper class.
     * It verifies that the method correctly converts the start data in PeriodDTO to a LocalDateTime object.
     */
    @Test
    void toStart() {
        //Arrange
        String expectedTimeStamp = "2021-01-01T00:00:00";
        //Act
        TimeStamp result = periodMapper.toStart(periodDTO);
        //Assert
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = result.getValue().format(formatter);
        assertEquals(expectedTimeStamp, formattedDateTime);
    }

    /**
     * This test checks the toEnd method of the PeriodMapper class.
     * It verifies that the method correctly converts the end data in PeriodDTO to a LocalDateTime object.
     */
    @Test
    void toEnd() {
        //Arrange
        String expectedTimeStamp = "2021-01-01T00:00:00";
        //Act
        TimeStamp result = periodMapper.toEnd(periodDTO);
        //Assert
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = result.getValue().format(formatter);
        assertEquals(expectedTimeStamp, formattedDateTime);
    }
}
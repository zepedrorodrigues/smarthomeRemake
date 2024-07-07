package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.ReadingIdDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for ReadingMapper
 */
class ReadingMapperTest {

    ReadingMapper readingMapper;
    Reading reading;
    ReadingId readingId;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        readingMapper = new ReadingMapper();

        reading = mock(Reading.class);

        readingId = mock(ReadingId.class);
        when(readingId.getId()).thenReturn("1");

        Value value = mock(Value.class);
        when(value.valueToString()).thenReturn("1");

        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn("1");

        TimeStamp timeStamp = mock(TimeStamp.class);
        when(timeStamp.valueToString()).thenReturn("2021-05-05T12:00:00");

        when(reading.getIdentity()).thenReturn(readingId);
        when(reading.getValue()).thenReturn(value);
        when(reading.getSensorId()).thenReturn(sensorId);
        when(reading.getTime()).thenReturn(timeStamp);


    }

    /**
     * Tests that the toReadingDTO method returns a ReadingDTO object with the correct reading id.
     */
    @Test
    void toReadingDTOReadingId() {
        //Arrange
        String expectedId = "1";
        //Act
        ReadingDTO result = readingMapper.toReadingDTO(reading);
        //Assert
        assertEquals(expectedId, result.getId(), "The reading id should be correct");
    }

    /**
     * Tests that the toReadingDTO method returns a ReadingDTO object with the correct reading value.
     */
    @Test
    void toReadingDTOReadingValue() {
        //Arrange
        String expectedReadingValue = "1";
        //Act
        ReadingDTO result = readingMapper.toReadingDTO(reading);
        //Assert
        assertEquals(expectedReadingValue, result.getReadingValue(), "The reading value should be correct");
    }

    /**
     * Tests that the toReadingDTO method returns a ReadingDTO object with the correct timeStamp.
     */
    @Test
    void toReadingDTOTimeStamp() {
        //Arrange
        String expectedTimeStamp = "2021-05-05T12:00:00";
        //Act
        ReadingDTO result = readingMapper.toReadingDTO(reading);
        //Assert
        assertEquals(expectedTimeStamp, result.getTimestamp(), "The timestamp should be correct");
    }

    /**
     * Tests that the toReadingsDTO method returns a list of ReadingDTO objects with the correct size.
     */
    @Test
    void toReadingsDTO() {
        //Arrange
        Iterable<Reading> readings = List.of(reading);
        int expectedSize = 1;
        //Act
        List<ReadingDTO> result = readingMapper.toReadingsDTO(readings);
        //Assert
        assertEquals(expectedSize, result.size(), "The list of reading DTOs should have one element");
    }

    /**
     * Tests that the toReadingIdsDTO method returns a list of ReadingIdDTO objects with the correct size.
     */
    @Test
    void toReadingIdsDTO() {
        //Arrange
        Iterable<ReadingId> readingIds = List.of(readingId);
        int expectedSize = 1;
        //Act
        List<ReadingIdDTO> result = readingMapper.toReadingIdsDTO(readingIds);
        //Assert
        assertEquals(expectedSize, result.size(), "The list of reading ID DTOs should have one element");
    }
}
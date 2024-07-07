package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ReadingRepositoryMemImpl class.
 */
class ReadingRepositoryMemImplTest {

    private ReadingRepositoryMemImpl readingRepositoryMemImpl;
    private Reading readingDouble1;
    private Reading readingDouble2;
    private ReadingId readingIdDouble1;
    private ReadingId readingIdDouble2;
    private SensorId sensorId;
    private TimeStamp start;
    private TimeStamp end;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        readingRepositoryMemImpl = new ReadingRepositoryMemImpl();
        sensorId = mock(SensorId.class);
        readingDouble1 = mock(Reading.class);
        readingDouble2 = mock(Reading.class);
        readingIdDouble1 = mock(ReadingId.class);
        readingIdDouble2 = mock(ReadingId.class);

        start = mock(TimeStamp.class);
        end = mock(TimeStamp.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);

        when(readingDouble1.getIdentity()).thenReturn(readingIdDouble1);
        when(readingDouble1.getSensorId()).thenReturn(sensorId);
        when(readingDouble1.getTime()).thenReturn(timeStamp1);

        when(readingDouble2.getIdentity()).thenReturn(readingIdDouble2);
        when(readingDouble2.getSensorId()).thenReturn(mock(SensorId.class));
        when(readingDouble2.getTime()).thenReturn(mock(TimeStamp.class));

        when(start.getValue()).thenReturn(LocalDateTime.of(2022, 2, 1, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2022, 2, 15, 0, 0));
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2022, 2, 7, 0, 0));
    }

    /**
     * Test that the save method saves a reading to an empty repository.
     * The method should return the saved reading.
     */
    @Test
    void testSaveToAnEmptyRepository() {
        //Act
        Reading result = readingRepositoryMemImpl.save(readingDouble1);

        //Assert
        assertEquals(result, readingDouble1, "The save method should save a reading to an empty repository and " +
                "return" + " the saved reading.");
    }

    /**
     * Test that the save method saves a reading to a non-empty repository.
     * The method should return the saved reading.
     */
    @Test
    void testSaveToANonEmptyRepository() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        Reading result = readingRepositoryMemImpl.save(readingDouble2);

        //Assert
        assertEquals(result, readingDouble2,
                "The save method should save a reading to a non-empty repository and " + "return the saved reading.");
    }

    /**
     * Test that the save method throws an exception when the reading is null.
     */
    @Test
    void testSaveNullReadingShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> readingRepositoryMemImpl.save(null),
                "The save method should " + "throw an exception when the reading is null.");
    }

    /**
     * Test that the save method doesn't throw an exception when the reading is not null.
     */
    @Test
    void testSaveNotNullReadingShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> readingRepositoryMemImpl.save(readingDouble1), "The save method should not throw an " +
                "exception when the reading is not null.");
    }

    /**
     * Test that the save method throws an exception when the reading id already exists in the repository.
     */
    @Test
    void testSaveDuplicateReadingShouldThrowException() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> readingRepositoryMemImpl.save(readingDouble1), "The save " +
                "method should throw an exception when the reading id already exists in the repository.");
    }

    /**
     * Test that the findAll method returns an empty iterable
     * when the repository is empty.
     */
    @Test
    void testFindAllToAnEmptyRepository() {
        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(), "The findAll method should return an empty iterable collection when the " +
                "repository is empty.");
    }

    /**
     * Test that the findAll method returns an iterable with all readings in the repository.
     */
    @Test
    void testFindAllToANonEmptyRepositoryShouldReturnAllReadings() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);
        int size = 2;

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertEquals(size, result.size(), "The findAll method should return an iterable with all readings in the " +
                "repository.");
    }

    /**
     * Test that the findAll method returns an iterable with all readings in the repository.
     */
    @Test
    void testFindAllToANonEmptyRepositoryShouldContainAllReadings() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1), "The findAll method should return " + "an iterable with all " +
                "readings in the repository, in this case, readingDouble1.");
        assertTrue(result.contains(readingDouble2), "The findAll method should return " + "an iterable with all " +
                "readings in the repository, in this case, readingDouble2.");
    }

    /**
     * Test that the getByIdentity method returns an empty optional
     * when the repository is empty.
     */
    @Test
    void testFindByIdentityToAnEmptyRepository() {
        //Act
        Optional<Reading> result = readingRepositoryMemImpl.findByIdentity(readingIdDouble1);

        //Assert
        assertTrue(result.isEmpty(), "The getByIdentity method should return an empty optional when the repository " +
                "is" + " empty.");
    }

    /**
     * Test that the getByIdentity method returns the correct reading
     * when the repository is non-empty.git stat
     */
    @Test
    void testFindByIdentityToANonEmptyRepository() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        Reading result = readingRepositoryMemImpl.findByIdentity(readingIdDouble1).get();

        //Assert
        assertEquals(result, readingDouble1,
                "The getByIdentity method should return the correct reading when the " + "repository is non-empty.");
    }

    /**
     * Test that the getByIdentity method throws an exception when the reading id is null.
     */
    @Test
    void testFindByIdentityNullReadingIdShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> readingRepositoryMemImpl.findByIdentity(null), "The " +
                "getByIdentity method should throw an exception when the reading id is null.");
    }

    /**
     * Test that the getByIdentity method doesn't throw an exception when the reading id is not null.
     */
    @Test
    void testFindByIdentityNotNullReadingIdShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> readingRepositoryMemImpl.findByIdentity(readingIdDouble1), "The getByIdentity method " +
                "should not throw an exception when the reading id is not null.");
    }

    /**
     * Test that the containsIdentity method returns true when the reading id
     * exists in the repository.
     */
    @Test
    void testContainsIdentityReturnsTrueWhenReadingIdExists() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        boolean result = readingRepositoryMemImpl.containsIdentity(readingIdDouble1);

        //Assert
        assertTrue(result, "The containsIdentity method should return true when the reading id exists in the " +
                "repository.");
    }

    /**
     * Test that the containsIdentity method returns false when the reading id
     * doesn't exist in the repository.
     */
    @Test
    void testContainsIdentityReturnsFalseWhenReadingIdDoesntExist() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        boolean result = readingRepositoryMemImpl.containsIdentity(readingIdDouble2);

        //Assert
        assertFalse(result, "The containsIdentity method should return false when the reading id doesn't exist in " +
                "the" + " repository.");
    }

    /**
     * Test that the containsIdentity method throws an exception when the reading id is null.
     */
    @Test
    void testContainsIdentityNullReadingIdShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> readingRepositoryMemImpl.containsIdentity(null), "The " +
                "containsIdentity method should throw an exception when the reading id is null.");
    }

    /**
     * Test that the containsIdentity method doesn't throw an exception when the reading id is not null.
     */
    @Test
    void testContainsIdentityNotNullReadingIdShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> readingRepositoryMemImpl.containsIdentity(readingIdDouble1), "The containsIdentity " +
                "method should not throw an exception when the reading id is not null.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns an empty iterable
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodFromAnEmptyRepository() {
        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(), "The findBySensorIdInAGivenPeriod method should return an empty iterable " +
                "collection when the repository is empty.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns an iterable with all readings that match the criteria.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenOnlyOneReadingMatchesCriteriaShouldReturnOnlyOneReading() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);
        int size = 1;

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertEquals(result.size(), size, "The findBySensorIdInAGivenPeriod method should return an iterable " +
                "collection of readings that match the criteria, in this case, only one reading.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns an iterable with all readings that match the criteria.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenOnlyOneReadingMatchesCriteriaShouldContainOnlyOneReading() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1), "The findBySensorIdInAGivenPeriod method should return an " +
                "iterable collection of readings " + "that match the criteria, in this case, only one reading.");
        assertFalse(result.contains(readingDouble2), "The findBySensorIdInAGivenPeriod method should return an " +
                "iterable collection of readings " + "that match the criteria, in this case, only one reading.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod with valid parameters returns an iterable with all readings that match the criteria.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWithValidParameters() {
        //Arrange
        String ID = "ID1";
        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn(ID);
        TimeStamp timeStamp = mock(TimeStamp.class);
        when(timeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 2, 14, 23, 15));
        when(readingDouble1.getSensorId()).thenReturn(sensorId);
        when(readingDouble1.getTime()).thenReturn(timeStamp);
        when(readingDouble2.getSensorId()).thenReturn(mock(SensorId.class));
        when(readingDouble2.getTime()).thenReturn(mock(TimeStamp.class));

        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);
        int size = 1;

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1) && result.size() == size, "The findBySensorIdInAGivenPeriod " +
                "method should return an iterable collection of readings that match the criteria, in this " +
                "case, only one reading.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod with valid parameters returns an iterable with all readings that match the criteria.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWithValidParametersShouldReturnCorrectReadings() {
        //Arrange
        String ID = "ID1";
        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn(ID);
        TimeStamp timeStamp = mock(TimeStamp.class);
        when(timeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 2, 14, 23, 15));
        when(readingDouble1.getSensorId()).thenReturn(sensorId);
        when(readingDouble1.getTime()).thenReturn(timeStamp);
        when(readingDouble2.getSensorId()).thenReturn(mock(SensorId.class));
        when(readingDouble2.getTime()).thenReturn(mock(TimeStamp.class));

        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble1.");
        assertFalse(result.contains(readingDouble2),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble2.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns an iterable with all readings that match the criteria.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenReadingTimeStampIsAfterStartAndBeforeEnd() {
        //Arrange
        TimeStamp readingTimeStamp = mock(TimeStamp.class);
        when(readingTimeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 2, 5, 0, 0));
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble1.");
        assertFalse(result.contains(readingDouble2),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble2.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns no readings when the reading timestamp is before the start time.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenReadingTimeStampIsBeforeStart() {
        //Arrange
        TimeStamp readingTimeStamp = mock(TimeStamp.class);
        when(readingDouble1.getTime()).thenReturn(readingTimeStamp);
        when(readingTimeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 1, 31, 23, 59));
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertFalse(result.contains(readingDouble1),
                "The findBySensorIdInAGivenPeriod method should return " + "an " + "iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble1.");
        assertFalse(result.contains(readingDouble2),
                "The findBySensorIdInAGivenPeriod method should return " + "an " + "iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble2.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns no readings when the reading timestamp is after the end time.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenReadingTimeStampIsAfterEnd() {
        //Arrange
        TimeStamp readingTimeStamp = mock(TimeStamp.class);
        when(readingDouble1.getTime()).thenReturn(readingTimeStamp);
        when(readingTimeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 2, 15, 0, 1));
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertFalse(result.contains(readingDouble1),
                "The findBySensorIdInAGivenPeriod method should return " + "an " + "iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble1.");
        assertFalse(result.contains(readingDouble2),
                "The findBySensorIdInAGivenPeriod method should return " + "an " + "iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble2.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns a reading when the reading timestamp is equal to the start time.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenReadingTimeStampIsEqualToStart() {
        //Arrange
        when(readingDouble1.getTime()).thenReturn(start);
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble1.");
        assertFalse(result.contains(readingDouble2),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble2.");
    }

    /**
     * Test that the findBySensorIdInAGivenPeriod method returns a reading when the reading timestamp is equal to the end time.
     */
    @Test
    void testFindReadingsBySensorIdInAGivenPeriodWhenReadingTimeStampIsEqualToEnd() {
        //Arrange
        when(readingDouble1.getTime()).thenReturn(end);
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<Reading> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        assertTrue(result.contains(readingDouble1),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble1.");
        assertFalse(result.contains(readingDouble2),
                "The findBySensorIdInAGivenPeriod method should return an iterable collection of readings " +
                        "that match the criteria, in this case, readingDouble2.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an empty iterable collection when the repository is empty.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodFromAnEmptyRepository() {
        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The method should return an empty iterable collection when the repository is empty.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This method asserts that the method returns an iterable collection of reading ids that match the criteria.
     * In this case, the size of the collection should be 1.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodWhenOnlyOneReadingMatchesCriteriaShouldReturnOnlyOneReading() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);
        int expectedSize = 1;

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize,
                "The method should return an iterable collection of reading ids that match the criteria, in this" +
                        " case, only one reading.");

    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids that match the criteria.
     * The test passes if the reading id matches the expected reading id.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodWhenOnlyOneReadingMatchesCriteriaShouldContainOnlyOneReading() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble1 = result.contains(readingIdDouble1);
        assertTrue(containsReadingDouble1, "The method should return an iterable collection of reading ids that " +
                "match the criteria, in this case, readingIdDouble1.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids that match the criteria.
     * The test passes if the reading id does not match the expected reading id.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodWhenOnlyOneReadingMatchesCriteriaShouldNotContainOtherReading() {
        //Arrange
        readingRepositoryMemImpl.save(readingDouble1);
        readingRepositoryMemImpl.save(readingDouble2);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble2 = result.contains(readingIdDouble2);
        assertFalse(containsReadingDouble2, "The method should return an iterable collection of reading ids " + "that" +
                " match the criteria, in this case, readingIdDouble2.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids if the reading timestamp is
     * after the start time and before the end time.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodReadingTimeStampAfterStartAndBeforeEnd() {
        //Arrange
        TimeStamp readingTimeStamp = mock(TimeStamp.class);
        when(readingTimeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 2, 5, 0, 0));
        when(readingDouble1.getTime()).thenReturn(readingTimeStamp);
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble1 = result.contains(readingIdDouble1);
        assertTrue(containsReadingDouble1, "The method should return an iterable collection of reading ids that " +
                "match the criteria, in this case, readingIdDouble1.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids if the reading timestamp is
     * before the start time.
     * The test passes if the method returns a collection that does not contain the reading id.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodReadingTimeStampBeforeStart() {
        //Arrange
        TimeStamp readingTimeStamp = mock(TimeStamp.class);
        when(readingTimeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 1, 31, 23, 59));
        when(readingDouble1.getTime()).thenReturn(readingTimeStamp);
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble1 = result.contains(readingIdDouble1);
        assertFalse(containsReadingDouble1, "The method should return an iterable collection of reading ids that " +
                "match the criteria, in this case, readingIdDouble1.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids if the reading timestamp is
     * after the end time.
     * The test passes if the method returns a collection that does not contain the reading id.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodReadingTimeStampAfterEnd() {
        //Arrange
        TimeStamp readingTimeStamp = mock(TimeStamp.class);
        when(readingTimeStamp.getValue()).thenReturn(LocalDateTime.of(2022, 2, 15, 0, 1));
        when(readingDouble1.getTime()).thenReturn(readingTimeStamp);
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble1 = result.contains(readingIdDouble1);
        assertFalse(containsReadingDouble1, "The method should return an iterable collection of reading ids that " +
                "match the criteria, in this case, readingIdDouble1.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids if the reading timestamp is
     * equal to the start time.
     * The test passes if the method returns a collection that contains the reading id.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodReadingTimeStampEqualToStart() {
        //Arrange
        when(readingDouble1.getTime()).thenReturn(start);
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble1 = result.contains(readingIdDouble1);
        assertTrue(containsReadingDouble1, "The method should return an iterable collection of reading ids that " +
                "match the criteria, in this case, readingIdDouble1.");
    }

    /**
     * Test the findReadingIdsBySensorIdInAGivenPeriod method.
     * This test asserts that the method returns an iterable collection of reading ids if the reading timestamp is
     * equal to the end time.
     * The test passes if the method returns a collection that contains the reading id.
     */
    @Test
    void testFindReadingIdsBySensorIdInAGivenPeriodReadingTimeStampEqualToEnd() {
        //Arrange
        when(readingDouble1.getTime()).thenReturn(end);
        readingRepositoryMemImpl.save(readingDouble1);

        //Act
        List<ReadingId> result = new ArrayList<>();
        readingRepositoryMemImpl.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end).forEach(result::add);

        //Assert
        boolean containsReadingDouble1 = result.contains(readingIdDouble1);
        assertTrue(containsReadingDouble1, "The method should return an iterable collection of reading ids that " +
                "match the criteria, in this case, readingIdDouble1.");
    }

    /**
     * Test the findTopBySensorIdOrderByTimeStampDesc method.
     * This test asserts that the method returns the latest reading with the given sensor id when the repository
     * has more than one reading.
     */
    @Test
    void testFindLastReadingBySensorIdWhenRepositoryWithMoreThanOneReading() {
        //Arrange
        String id = "sensorId";
        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn(id);
        readingRepositoryMemImpl.save(readingDouble1);
        when(readingDouble1.getSensorId()).thenReturn(sensorId);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2022, 2, 14, 23, 15));
        when(readingDouble1.getTime()).thenReturn(timeStamp1);
        readingRepositoryMemImpl.save(readingDouble2);
        when(readingDouble2.getSensorId()).thenReturn(sensorId);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2022, 2, 14, 23, 16));
        when(readingDouble2.getTime()).thenReturn(timeStamp2);

        //Act
        Optional<Reading> result = readingRepositoryMemImpl.findLastReadingBySensorId(sensorId);

        //Assert
        assertTrue(result.isPresent() && readingDouble2.equals(result.get()),
                "The method should return the latest reading with the given sensor id.");
    }

    /**
     * Test the findTopBySensorIdOrderByTimeStampDesc method.
     * This test asserts that the method returns the latest reading with the given sensor
     * id when the repository has only one reading.
     */
    @Test
    void testFindLastReadingBySensorIdWhenRepositoryWitOneReading() {
        //Arrange
        String id = "sensorId";
        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn(id);
        readingRepositoryMemImpl.save(readingDouble1);
        when(readingDouble1.getSensorId()).thenReturn(sensorId);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2022, 2, 14, 23, 15));
        when(readingDouble1.getTime()).thenReturn(timeStamp1);

        //Act
        Optional<Reading> result = readingRepositoryMemImpl.findLastReadingBySensorId(sensorId);

        //Assert
        assertTrue(result.isPresent() && readingDouble1.equals(result.get()),
                "The method should return the latest reading with the given sensor id.");
    }

    /**
     * Test the findTopBySensorIdOrderByTimeStampDesc method.
     * This test asserts that the method returns an empty optional when the repository is empty.
     */
    @Test
    void testFindLastReadingBySensorIdWhenRepositoryIsEmpty() {
        //Arrange
        String id = "sensorId";
        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn(id);
        //Act
        Optional<Reading> result = readingRepositoryMemImpl.findLastReadingBySensorId(sensorId);

        //Assert
        assertTrue(result.isEmpty(), "The method should return an empty optional when the repository is empty.");
    }

}
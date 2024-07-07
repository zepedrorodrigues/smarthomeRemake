package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.ReadingFactory;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.persistence.datamodel.ReadingDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps data model objects to domain model objects for readings.
 * This class is responsible for converting {@link ReadingDataModel} instances,
 * which are typically retrieved from a database, into {@link Reading} domain objects
 * that are used throughout the application.
 */
@Component
public class ReadingDataModelMapper {
    private final ReadingFactory readingFactory;

    /**
     * Constructs a new {@code ReadingDataModelMapper} with a specified {@code IReadingFactory}.
     *
     * @param readingFactory the factory used to create domain model objects from data model objects
     */
    public ReadingDataModelMapper(ReadingFactory readingFactory) {
        this.readingFactory = readingFactory;
    }

    /**
     * Converts a {@link ReadingDataModel} to a {@link Reading} domain model object.
     *
     * @param readingDataModel the data model object to convert
     * @return the domain model object created from the data model
     */
    public Reading toReadingDomainModel(ReadingDataModel readingDataModel) {
        ReadingId readingId = new ReadingId(readingDataModel.getReadingId());
        SensorId sensorId = new SensorId(readingDataModel.getSensorId());
        TimeStamp timeStamp = new TimeStamp(readingDataModel.getTimeStamp());
        ReadingValue valueString = new ReadingValue(readingDataModel.getReadingValue());
        return readingFactory.createReading(readingId, valueString, sensorId, timeStamp);
    }

    /**
     * Converts an iterable collection of {@link ReadingDataModel} to a list of {@link Reading} domain models.
     *
     * @param readingDataModels the iterable collection of data model objects to convert
     * @return a list of domain model objects created from the data models
     */
    public Iterable<Reading> toReadingDomainModels(Iterable<ReadingDataModel> readingDataModels) {
        List<Reading> readings = new ArrayList<>();
        for (ReadingDataModel reading : readingDataModels) {
            readings.add(toReadingDomainModel(reading));
        }
        return readings;
    }
}

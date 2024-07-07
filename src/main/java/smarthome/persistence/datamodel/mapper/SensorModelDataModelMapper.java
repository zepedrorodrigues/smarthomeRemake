package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.SensorModelFactory;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.persistence.datamodel.SensorModelDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The SensorModelDataModelMapper class is responsible for mapping SensorModelDataModel objects to SensorModel objects.
 */
@Component
public class SensorModelDataModelMapper {

    private final SensorModelFactory sensorModelFactory;


    /**
     * Constructs a SensorModelDataModelMapper object with the given SensorModelFactory.
     *
     * @param sensorModelFactory The SensorModelFactory object to be used for creating SensorModel objects.
     */
    public SensorModelDataModelMapper(SensorModelFactory sensorModelFactory) {
        this.sensorModelFactory = sensorModelFactory;
    }

    /**
     * Maps a SensorModelDataModel object to a SensorModel object.
     *
     * @param sensorModelDataModel The SensorModelDataModel object to be mapped.
     * @return The SensorModel object.
     */
    public SensorModel toSensorModelDomain(SensorModelDataModel sensorModelDataModel) {
        SensorModelName sensorModelName = new SensorModelName(sensorModelDataModel.getSensorModelName());
        SensorTypeId sensorTypeId = new SensorTypeId(sensorModelDataModel.getSensorTypeId());
        return sensorModelFactory.createSensorModel(sensorModelName, sensorTypeId);
    }

    /**
     * Maps an Iterable of SensorModelDataModel objects to an Iterable of SensorModel objects.
     *
     * @param sensorModelsDataModel The Iterable of SensorModelDataModel objects to be mapped.
     * @return The Iterable of SensorModel objects.
     */
    public Iterable<SensorModel> toSensorModelsDomain(Iterable<SensorModelDataModel> sensorModelsDataModel) {
        List<SensorModel> sensorModels = new ArrayList<>();

        for (SensorModelDataModel sensorModelDataModel : sensorModelsDataModel) {
            sensorModels.add(toSensorModelDomain(sensorModelDataModel));
        }
        return sensorModels;
    }

    /**
     * Maps a list of SensorModelDataModel objects to a list of SensorModelName objects.
     *
     * @param sensorModelDataModels The list of SensorModelDataModel objects to be mapped.
     * @return A list of SensorModelName objects.
     * @throws IllegalArgumentException if the sensorModelDataModels is null.
     */
    public List<SensorModelName> toSensorModelNamesDomain(List<SensorModelDataModel> sensorModelDataModels) {
        if (sensorModelDataModels == null) {
            throw new IllegalArgumentException();
        }
        List<SensorModelName> sensorModelNames = new ArrayList<>();
        for (SensorModelDataModel sensorModelDataModel : sensorModelDataModels) {
            sensorModelNames.add(new SensorModelName(sensorModelDataModel.getSensorModelName()));
        }
        return sensorModelNames;
    }
}

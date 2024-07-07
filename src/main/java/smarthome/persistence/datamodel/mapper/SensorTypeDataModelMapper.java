package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between the SensorType domain model and the SensorTypeDataModel.
 * It uses the SensorTypeFactory to create SensorType domain models.
 */
@Component
public class SensorTypeDataModelMapper {
    private final SensorTypeFactory sensorTypeFactory;

    /**
     * Constructor for the SensorTypeDataModelMapper class.
     *
     * @param sensorTypeFactory The factory to create SensorType domain models.
     */
    public SensorTypeDataModelMapper(SensorTypeFactory sensorTypeFactory) {
        this.sensorTypeFactory = sensorTypeFactory;
    }

    /**
     * This method converts a SensorTypeDataModel to a SensorType domain model.
     *
     * @param sensorTypeDataModel The SensorTypeDataModel to be converted.
     * @return The converted SensorType domain model.
     */
    public SensorType toSensorTypeDomain(smarthome.persistence.datamodel.SensorTypeDataModel sensorTypeDataModel) {
        SensorTypeId sensorTypeId = new SensorTypeId(sensorTypeDataModel.getSensorTypeId());
        SensorTypeName sensorTypeName = new SensorTypeName(sensorTypeDataModel.getSensorTypeName());
        SensorTypeUnit sensorTypeUnit = new SensorTypeUnit(sensorTypeDataModel.getSensorTypeUnit());
        return sensorTypeFactory.createSensorType(sensorTypeId, sensorTypeName, sensorTypeUnit);
    }

    /**
     * This method converts an Iterable of SensorTypeDataModels to an Iterable of SensorType domain models.
     *
     * @param listSensorTypeDataModel The Iterable of SensorTypeDataModels to be converted.
     * @return The converted Iterable of SensorType domain models.
     */
    public Iterable<SensorType> toSensorTypesDomain(Iterable<smarthome.persistence.datamodel.SensorTypeDataModel> listSensorTypeDataModel) {
        List<SensorType> listDomain = new ArrayList<>();
        for (smarthome.persistence.datamodel.SensorTypeDataModel sensorTypeDataModel : listSensorTypeDataModel) {
            listDomain.add(toSensorTypeDomain(sensorTypeDataModel));
        }
        return listDomain;
    }
}
